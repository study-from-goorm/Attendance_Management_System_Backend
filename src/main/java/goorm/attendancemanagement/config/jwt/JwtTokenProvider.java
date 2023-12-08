package goorm.attendancemanagement.config.jwt;

import goorm.attendancemanagement.config.auth.AdminDetails;
import goorm.attendancemanagement.config.auth.PlayerDetails;
import goorm.attendancemanagement.domain.dao.Admin;
import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.domain.dao.RefreshToken;
import goorm.attendancemanagement.domain.dto.ReissueRequestDto;
import goorm.attendancemanagement.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static goorm.attendancemanagement.config.jwt.JwtProperties.SECRET_KEY;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final RefreshTokenRepository refreshTokenRepository;

    // 토큰 생성
    public String[] createToken(Authentication authentication) {
        String roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken =  Jwts.builder()
                .subject(authentication.getName())         // JWT payload 에 저장되는 정보단위
                .claim("roles", roles)              // 정보는 key / value 쌍으로 저장
                .expiration(new Date(new Date().getTime() + JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME))    // 토큰 유효시각 설정
                .signWith(SECRET_KEY)    // 암호화 알고리즘과, secret 값
                .compact();

        String refreshToken = Jwts.builder()
                .expiration(new Date(new Date().getTime() + JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();

        refreshTokenRepository.save(new RefreshToken(authentication.getName(), refreshToken));

        return new String[]{accessToken, refreshToken};

    }

    // 인증 정보 조회
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();
        String authority = authorities.stream().map(GrantedAuthority::getAuthority).findAny().orElse("");

        if (authority.equals("ROLE_ADMIN")) {
            Admin admin = new Admin(claims.getSubject(), "", authorities.toString());
            AdminDetails adminDetails = new AdminDetails(admin);
            return new UsernamePasswordAuthenticationToken(adminDetails, "", authorities);
        } else if (authority.equals("ROLE_USER")){
            Player player = new Player(claims.getSubject(), "", "", null, authorities.toString());
            PlayerDetails playerDetails = new PlayerDetails(player);
            return new UsernamePasswordAuthenticationToken(playerDetails, "", authorities);
        } else {
            return null;
        }
    }

    // 토큰 재발급
    public ResponseEntity<?> reissue(ReissueRequestDto dto) {
        String reissueAccessToken = dto.getAccessToken().replace("Bearer ", "");
        String reissueRefreshToken = dto.getRefreshToken().replace("Bearer ", "");

        if (!validateToken(reissueRefreshToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh Token 정보가 유효하지 않습니다.");
        }
        Authentication authentication = getAuthentication(reissueAccessToken);
        String refreshToken = refreshTokenRepository.findById(authentication.getName()).get().getRefreshToken();
        if (refreshToken.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
        if (!refreshToken.equals(reissueRefreshToken)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Refresh Token 정보가 일치하지 않습니다.");
        }

        String[] tokens = createToken(authentication);
        refreshTokenRepository.save(new RefreshToken(authentication.getName(), tokens[1]));

        HttpHeaders headers = new HttpHeaders();
        headers.add("accessToken", tokens[0]);
        headers.add("refreshToken", tokens[1]);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    // 토큰 유효성 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token);
            return true;
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch(ExpiredJwtException e) {
            System.out.println("만료된 토큰입니다.");
        }catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}

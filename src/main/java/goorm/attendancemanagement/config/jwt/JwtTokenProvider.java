package goorm.attendancemanagement.config.jwt;

import goorm.attendancemanagement.config.auth.AdminDetails;
import goorm.attendancemanagement.domain.dao.Admin;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static goorm.attendancemanagement.config.jwt.JwtProperties.SECRET_KEY;

@Component
public class JwtTokenProvider {

    // 토큰 생성
    public String createToken(AdminDetails adminDetails) {
        String roles = adminDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.joining(","));

        return Jwts.builder()
                .subject(adminDetails.getAdmin().getAdminId())  // JWT payload 에 저장되는 정보단위
                .claim("roles", roles)   // 정보는 key / value 쌍으로 저장
                .issuedAt(new Date())                           // 토큰 발행 시간 정보
                .expiration(new Date(new Date().getTime() + JwtProperties.EXPIRATION_TIME))    // 토큰 유효시각 설정
                .signWith(SECRET_KEY)    // 암호화 알고리즘과, secret 값
                .compact();
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

        Admin admin = new Admin(claims.getSubject(), "", authorities.toString());
        AdminDetails adminDetails = new AdminDetails(admin);

        return new UsernamePasswordAuthenticationToken(adminDetails, "", authorities);
    }

    // 토큰 유효성, 만료일자 확인
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

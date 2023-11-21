package goorm.attendancebook.jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenProvider {

    // JWT 생성 및 검증을 위한 키 (안전한 키로 생성)
    private static final Key SECURITY_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    // JWT 생성하는 메서드
    public String createToken(String subject) {
        // 만료날짜를 현재 날짜 + 1시간으로 설정
        Date expiryTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // JWT 생성
        return Jwts.builder()
                .signWith(SECURITY_KEY, SignatureAlgorithm.HS512)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(expiryTime)
                .compact();
    }

    public String createAdminToken(String adminId, String adminRole) {
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        return Jwts.builder()
                .signWith(SECURITY_KEY, SignatureAlgorithm.HS512)
                .setSubject(adminId)
                .claim("role", adminRole) // 역할 정보를 토큰에 포함
                .setIssuedAt(new Date())
                .setExpiration(exprTime)
                .compact();
    }

    // JWT 검증 메서드
    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECURITY_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
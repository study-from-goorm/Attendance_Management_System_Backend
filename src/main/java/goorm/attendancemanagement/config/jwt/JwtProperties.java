package goorm.attendancemanagement.config.jwt;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public interface JwtProperties {

    SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    long EXPIRATION_TIME = 60 * 60 * 1000L;     // 토큰 유효시간 60분
    String HEADER_STRING = "jwt";
}

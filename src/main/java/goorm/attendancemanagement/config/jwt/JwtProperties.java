package goorm.attendancemanagement.config.jwt;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public interface JwtProperties {

    SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();
    long ACCESS_TOKEN_EXPIRATION_TIME = 30 * 60 * 1000L;     // 토큰 유효시간 30분
    long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L;     // 토큰 유효시간 7일
}

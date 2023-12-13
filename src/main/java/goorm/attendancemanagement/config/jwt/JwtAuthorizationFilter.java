package goorm.attendancemanagement.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("accessToken");
        if(header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token =  header.replace("Bearer ", "");
        String requestURI = request.getRequestURI();

        String validation = jwtTokenProvider.validateToken(token);

        if(validation.equals("success")) {
            SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
        } else {
            logger.info("유효한 JWT토큰이 없습니다. uri:"+requestURI);
            response.addHeader("validation", validation);
        }
        chain.doFilter(request, response);
    }
}





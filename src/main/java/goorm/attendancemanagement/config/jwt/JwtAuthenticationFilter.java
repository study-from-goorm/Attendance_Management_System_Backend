package goorm.attendancemanagement.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.attendancemanagement.domain.dto.LoginRequestDto;
import goorm.attendancemanagement.domain.dto.LoginResponseDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final ObjectMapper om = new ObjectMapper();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, String url) {
        super(authenticationManager);
        setFilterProcessesUrl(url);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper om = new ObjectMapper();
        LoginRequestDto loginRequestDto = null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(),
                        loginRequestDto.getPassword());

        System.out.println("토큰 생성 완료");

        try {
            return this.getAuthenticationManager().authenticate(authenticationToken);
        } catch (NullPointerException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String token = jwtTokenProvider.createToken(authResult);
        String role = authResult.getAuthorities().stream().map(GrantedAuthority::getAuthority).findAny().orElse(null);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setAccessToken("Bearer " + token);
        loginResponseDto.setRole(role);

        String result = om.writeValueAsString(loginResponseDto);
        response.getWriter().write(result);
    }
}

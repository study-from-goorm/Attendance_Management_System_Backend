package goorm.attendancemanagement.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import goorm.attendancemanagement.config.auth.AdminDetails;
import goorm.attendancemanagement.domain.dto.LoginRequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

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
                        loginRequestDto.getName(),
                        loginRequestDto.getPassword());

        System.out.println("토큰 생성 완료");

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        AdminDetails adminDetails = (AdminDetails) authentication.getPrincipal();
        System.out.println("Authentication:" + adminDetails.getAdmin().getAdminId());
        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AdminDetails adminDetails = (AdminDetails) authResult.getPrincipal();
        String jwt = jwtTokenProvider.createToken(adminDetails);
        response.addHeader("jwt", "Bearer " + jwt);
    }
}

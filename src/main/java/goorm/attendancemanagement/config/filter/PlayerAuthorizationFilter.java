package goorm.attendancemanagement.config.filter;

import goorm.attendancemanagement.config.auth.PlayerDetails;
import goorm.attendancemanagement.config.jwt.JwtTokenProvider;
import goorm.attendancemanagement.repository.PlayerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class PlayerAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!request.getRequestURI().startsWith("/player")){
            filterChain.doFilter(request, response);
            return;
        }

        int requestPlayerId = request.getRequestURI().chars().filter(c -> '0' <= c && c <= '9').findFirst().getAsInt();
        String token = request.getHeader("accessToken").replace("Bearer ", "");
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        PlayerDetails playerDetails = (PlayerDetails) authentication.getPrincipal();
        char playerId = playerDetails.getUsername().charAt(0);

        if (playerId != requestPlayerId){
            log.info("권한이 없습니다. 현재 playerId={}, 조회 playerId={}", requestPlayerId, playerId);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(playerDetails, "", new ArrayList<>()));
        }

        filterChain.doFilter(request, response);
    }
}

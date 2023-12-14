package goorm.attendancemanagement.config.filter;

import goorm.attendancemanagement.config.auth.PlayerDetails;
import goorm.attendancemanagement.config.jwt.JwtTokenProvider;
import goorm.attendancemanagement.repository.PlayerRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class PlayerAuthorizationFilter extends BasicAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();
    private final PlayerRepository playerRepository;

    public PlayerAuthorizationFilter(AuthenticationManager authenticationManager, PlayerRepository playerRepository) {
        super(authenticationManager);
        this.playerRepository = playerRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(!request.getRequestURI().startsWith("/player")){
            chain.doFilter(request, response);
            return;
        }

        String requestURI = request.getRequestURI();
        String token = request.getHeader("accessToken").replace("Bearer ", "");
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        PlayerDetails playerDetails = (PlayerDetails) authentication.getPrincipal();
        int playerId = playerRepository.findByPlayerEmail(playerDetails.getUsername()).getPlayerId();

        if (playerId != requestURI.charAt(8) - '0') {
            log.info("권한이 없습니다. 현재 playerId={}, 조회 playerId={}", requestURI.charAt(8), playerId);
            authentication.setAuthenticated(false);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}

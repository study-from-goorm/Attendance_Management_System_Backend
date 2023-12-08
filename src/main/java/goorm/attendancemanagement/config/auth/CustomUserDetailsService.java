package goorm.attendancemanagement.config.auth;

import goorm.attendancemanagement.domain.dao.Admin;
import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.repository.AdminRepository;
import goorm.attendancemanagement.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        if(id.contains("@")){
            Player player = playerRepository.findByPlayerEmail(id);
            return new PlayerDetails(player);
        } else {
            Admin admin = adminRepository.findByAdminId(id);
            return new AdminDetails(admin);
        }
    }
}

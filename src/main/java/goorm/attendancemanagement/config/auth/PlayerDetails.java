package goorm.attendancemanagement.config.auth;

import goorm.attendancemanagement.domain.dao.Player;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PlayerDetails implements UserDetails {

    private final Player player;

    public PlayerDetails(Player player) { this.player = player; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(player::getRole);
        return authorities;
    }

    @Override
    public String getPassword() {
        return player.getPlayerPassword();
    }

    @Override
    public String getUsername() {
        return player.getPlayerEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

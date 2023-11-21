package goorm.attendancebook.service;

import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Player savePlayer(Player player) {
        String hashedPassword = passwordEncoder.encode(player.getPlayerPw());
        player.setPlayerPw(hashedPassword);
        return playerRepository.save(player);
    }

}
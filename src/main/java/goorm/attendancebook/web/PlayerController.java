package goorm.attendancebook.web;

import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    private final PlayerRepository playerRepository;

    @GetMapping("/findAll")
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

    @GetMapping("/findByPlayerName")
    public Optional<Player> findByPlayerName(@RequestParam String playerName) {
        return playerRepository.findByPlayerName(playerName);
    }

    @GetMapping("/findPlayer")
    public List<Player> findPlayer(@RequestParam String playerName, String playerEmail, String playerCourse){
        return playerRepository.findPlayerByPlayerNameAndPlayerEmailAndPlayerCourse(playerName, playerEmail, playerCourse);
    }
}

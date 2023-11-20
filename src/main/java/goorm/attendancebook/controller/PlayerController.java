package goorm.attendancebook.controller;

import goorm.attendancebook.domain.Player;
import goorm.attendancebook.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    private final PlayerRepository playerRepository;

    @GetMapping("/findAll")
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

}

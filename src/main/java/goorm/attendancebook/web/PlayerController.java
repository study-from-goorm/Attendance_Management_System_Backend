package goorm.attendancebook.web;

import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.repository.PlayerRepository;
import goorm.attendancebook.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerRepository playerRepository;

    @GetMapping("/findAll")
    public List<Player> findAll() {
        return playerRepository.findAll();
    }

}

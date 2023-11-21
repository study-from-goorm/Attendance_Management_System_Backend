package goorm.attendancebook.web;


import goorm.attendancebook.domain.dao.Attendance;
import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.repository.PlayerRepository;
import goorm.attendancebook.service.AdminService;
import goorm.attendancebook.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final PlayerRepository playerRepository;
    private final AdminService adminService;
    @PostMapping("/add")
    public Attendance createPlayer(@RequestBody Player player) {
        System.out.println(player);
        return adminService.addPlayer(player);
    }
}

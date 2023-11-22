package goorm.attendancebook.service;

import goorm.attendancebook.domain.dao.Attendance;
import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.repository.AttendanceRepository;
import goorm.attendancebook.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PlayerRepository playerRepository;
    private final AttendanceRepository attendanceRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Attendance addPlayer(Player player) {
        String hashedPassword = passwordEncoder.encode(player.getPlayerPw());
        player.setPlayerPw(hashedPassword);
        playerRepository.save(player);

        Attendance attendance = new Attendance();
        attendance.setPlayerId(player.getPlayerId());
        attendance.setAttendanceDate(LocalDate.now());

        return attendanceRepository.save(attendance);
    }

}

package goorm.attendancebook.service;


import goorm.attendancebook.domain.dao.Attendance;
import goorm.attendancebook.domain.dao.Player;
import goorm.attendancebook.repository.AttendanceRepository;
import goorm.attendancebook.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final PlayerRepository playerRepository;
    private final AttendanceRepository attendanceRepository;

    @Scheduled(cron = "0 1 0 * * *")
    public void createTodayAttendanceRecords() {
        List<Player> players = playerRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Player player : players) {
            Attendance attendance = new Attendance();
            attendance.setPlayer(player);
            attendance.setAttendanceDate(today);

            attendanceRepository.save(attendance);
        }
    }

}

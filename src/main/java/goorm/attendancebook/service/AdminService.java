package goorm.attendancebook.service;

import goorm.attendancebook.domain.AttendanceDate;
import goorm.attendancebook.domain.AttendanceSession;
import goorm.attendancebook.domain.Player;
import goorm.attendancebook.repository.DateRepository;
import goorm.attendancebook.repository.PlayerRepository;
import goorm.attendancebook.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PlayerRepository playerRepository;
    private final DateRepository dateRepository;
    private final SessionRepository sessionRepository;

    public Map<String, Map<Integer, Integer>> getSessionsForCourseOnDate(String course, LocalDate date) {
        List<Player> players = playerRepository.findByPlayerCourse(course);
        Map<String, Map<Integer, Integer>> playerSessions = new HashMap<>();

        for (Player player : players) {
            List<AttendanceDate> dates = dateRepository.findByPlayerIdAndAttendanceDate(player.getId(), date);
            for (AttendanceDate attendanceDate : dates) {
                List<AttendanceSession> sessions = sessionRepository.findByDateId(attendanceDate.getId());

                Map<Integer, Integer> sessionData = new HashMap<>();
                for (AttendanceSession session : sessions) {
                    sessionData.put(1, session.getSessionOne());
                    sessionData.put(2, session.getSessionTwo());
                    // ... Repeat for all session columns
                }

                playerSessions.put(player.getPlayerName(), sessionData);
            }
        }

        return playerSessions;
    }
}

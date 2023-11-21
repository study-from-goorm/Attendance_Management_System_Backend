package goorm.attendancebook.service;

import goorm.attendancebook.repository.AttendanceRepository;
import goorm.attendancebook.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final PlayerRepository playerRepository;
    private final AttendanceRepository attendanceRepository;

}

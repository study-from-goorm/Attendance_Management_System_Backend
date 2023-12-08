package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.AttendanceStatus;
import goorm.attendancemanagement.domain.dto.AttendanceDetailsDto;
import goorm.attendancemanagement.domain.dto.AttendanceSummaryDto;
import goorm.attendancemanagement.domain.dto.PlayerAttendanceDto;
import goorm.attendancemanagement.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;

    public AttendanceSummaryDto getAttendanceSummary(int playerId, int month) {
        List<PlayerAttendanceDto> attendanceList = attendanceRepository.findAttendanceInfoByPlayerIdAndMonth(playerId, month);

        String playerName = attendanceList.stream()
                .findFirst()
                .map(PlayerAttendanceDto::getPlayerName)
                .orElse(null);

        LocalDate startOfMonth = LocalDate.now().withMonth(month).withDayOfMonth(1);
        LocalDate endOfMonth = YearMonth.from(startOfMonth).atEndOfMonth();

        int totalDays = (int) IntStream.rangeClosed(1, endOfMonth.getDayOfMonth())
                .mapToObj(day -> startOfMonth.withDayOfMonth(day))
                .filter(date -> !isWeekend(date))
                .count();

        Map<AttendanceStatus, Long> statusCount = attendanceList.stream()
                .collect(Collectors.groupingBy(PlayerAttendanceDto::getAttendanceStatus, Collectors.counting()));

        List<AttendanceDetailsDto> attendanceDetails = attendanceList.stream()
                .map(dto -> new AttendanceDetailsDto(dto.getAttendanceDate(), dto.getAttendanceStatus()))
                .collect(Collectors.toList());

        Map<AttendanceStatus, Long> initialStatusCount = EnumSet.allOf(AttendanceStatus.class).stream()
                .collect(Collectors.toMap(Function.identity(), status -> 0L));

        // Compute actual status count from attendance list
        Map<AttendanceStatus, Long> actualStatusCount = attendanceList.stream()
                .collect(Collectors.groupingBy(PlayerAttendanceDto::getAttendanceStatus, Collectors.counting()));

        // Combine the initial map with actual counts
        initialStatusCount.putAll(actualStatusCount);

        // Calculate notEntered count
        long totalPresentCount = initialStatusCount.values().stream().mapToLong(Long::longValue).sum();
        long notEnteredCount = totalDays - totalPresentCount;
        initialStatusCount.put(AttendanceStatus.notEntered, notEnteredCount);

        // Create and return the summary DTO
        return new AttendanceSummaryDto(playerName, month, totalDays, initialStatusCount, attendanceDetails);
    }

    private boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
}

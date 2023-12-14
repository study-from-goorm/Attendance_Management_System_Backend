package goorm.attendancemanagement.service;

import goorm.attendancemanagement.domain.dao.Holiday;
import goorm.attendancemanagement.repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public void createHoliday(LocalDate date, String dateName) {
        holidayRepository.save(new Holiday(date, dateName));
    }
}

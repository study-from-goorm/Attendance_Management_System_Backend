package goorm.attendancemanagement;

import goorm.attendancemanagement.domain.dao.*;
import goorm.attendancemanagement.domain.dto.ApplicationRequestDto;
import goorm.attendancemanagement.domain.dto.CreatePlayerDto;
import goorm.attendancemanagement.service.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final AdminService adminService;
        private final CourseService courseService;
        private final PlayerService playerService;
        private final ApplicationService applicationService;
        private final HolidayService holidayService;

        public void dbInit() {
            adminService.createAdmin("goorm", "1234");

            holidayService.createHoliday(LocalDate.of(2023, 11, 29), "테스트");
            holidayService.createHoliday(LocalDate.of(2023, 11, 28), "테스트");
            holidayService.createHoliday(LocalDate.of(2023, 12, 25), "테스트");
            holidayService.createHoliday(LocalDate.of(2024, 2, 9), "설날");
            holidayService.createHoliday(LocalDate.of(2024, 2, 12), "설날");
            holidayService.createHoliday(LocalDate.of(2024, 3, 1), "삼일절");
            holidayService.createHoliday(LocalDate.of(2024, 4, 10), "22대 국회의원 선거");

            courseService.createCourse("풀스택 1회차", LocalDate.of(2023, 11, 8), LocalDate.of(2024, 5, 1), 4);
            courseService.createCourse("풀스택 2회차", LocalDate.of(2023, 11, 27), LocalDate.of(2024, 5, 9), 5);
            courseService.createCourse("풀스택 3회차", LocalDate.of(2023, 10, 13), LocalDate.of(2024, 4, 22), 6);

            playerService.createPlayer(new CreatePlayerDto(1, "김김김", "kim@goorm.io", "123456"));
            playerService.createPlayer(new CreatePlayerDto(2, "이이이", "lee@goorm.io", "234567"));
            playerService.createPlayer(new CreatePlayerDto(3, "최최최", "choi@goorm.io", "345678"));
            playerService.createPlayer(new CreatePlayerDto(1, "박박박", "park@goorm.io", "456789"));
            playerService.createPlayer(new CreatePlayerDto(2, "손손손", "son@goorm.io", "567890"));
            playerService.createPlayer(new CreatePlayerDto(3, "강강강", "kand@goorm.io", "678901"));
            playerService.createPlayer(new CreatePlayerDto(3, "황황황", "hwang@goorm.io", "789012"));

            applicationService.createApplication(1, new ApplicationRequestDto(LocalDate.now().plusDays(2), "휴가", "놀고싶어요"));
            applicationService.createApplication(4, new ApplicationRequestDto(LocalDate.now().plusDays(3), "공결", "아파요"));
            applicationService.createApplication(6, new ApplicationRequestDto(LocalDate.now().plusDays(5), "외출", "면접보러가요"));

//            Attendance attendance1 = createAttendance(
//                    player1, LocalDate.of(2023, 11, 30), AttendanceStatus.partiallyPresent,
//                   1,0, 0, 0, 1, 1, 1, 1);
//            em.persist(attendance1);
//            Attendance attendance2 = createAttendance(
//                    player2, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance2);
//            Attendance attendance3 = createAttendance(
//                    player3, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance3);
//            Attendance attendance4 = createAttendance(
//                    player4, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance4);
//            Attendance attendance5 = createAttendance(
//                    player5, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance5);
//            Attendance attendance6 = createAttendance(
//                    player6, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance6);
//            Attendance attendance7 = createAttendance(
//                    player7, LocalDate.of(2023, 11, 30), AttendanceStatus.partiallyPresent,
//                    1, 1, 0, 0, 1, 1, 1, 1);
//            em.persist(attendance7);
//
//            Attendance attendance8 = createAttendance(
//                    player1, LocalDate.of(2023, 12, 1), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance8);
//            Attendance attendance9 = createAttendance(
//                    player2, LocalDate.of(2023, 12, 1), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance9);
//            Attendance attendance10 = createAttendance(
//                    player3, LocalDate.of(2023, 12, 1), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance10);
//            Attendance attendance11 = createAttendance(
//                    player4, LocalDate.of(2023, 12, 1), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance11);
//            Attendance attendance12 = createAttendance(
//                    player5, LocalDate.of(2023, 12, 1), AttendanceStatus.onVacation,
//                    0, 0, 0, 0, 0, 0, 0, 0);
//            em.persist(attendance12);
//            Attendance attendance13 = createAttendance(
//                    player6, LocalDate.of(2023, 12, 1), AttendanceStatus.officiallyExcused,
//                    0, 0, 0, 0, 0, 0, 0, 0);
//            em.persist(attendance13);
//            Attendance attendance14 = createAttendance(
//                    player7, LocalDate.of(2023, 12, 1), AttendanceStatus.absent,
//                    0, 0, 0, 0, 0, 0, 0, 0);
//            em.persist(attendance14);
//
//            Attendance attendance15 = createAttendance(
//                    player1, LocalDate.of(2023, 12, 2), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance15);
//            Attendance attendance16 = createAttendance(
//                    player2, LocalDate.of(2023, 12, 2), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance16);
//            Attendance attendance17 = createAttendance(
//                    player3, LocalDate.of(2023, 12, 2), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance17);
//            Attendance attendance18 = createAttendance(
//                    player4, LocalDate.of(2023, 12, 2), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance18);
//            Attendance attendance19 = createAttendance(
//                    player5, LocalDate.of(2023, 12, 2), AttendanceStatus.onVacation,
//                    0, 0, 0, 0, 0, 0, 0, 0);
//            em.persist(attendance19);
//            Attendance attendance20 = createAttendance(
//                    player6, LocalDate.of(2023, 12, 2), AttendanceStatus.officiallyExcused,
//                    0, 0, 0, 0, 0, 0, 0, 0);
//            em.persist(attendance20);
//            Attendance attendance21 = createAttendance(
//                    player7, LocalDate.of(2023, 12, 2), AttendanceStatus.absent,
//                    0, 0, 0, 0, 0, 0, 0, 0);
//            em.persist(attendance21);
//
//            Attendance attendance22 = createAttendance(
//                    player1, LocalDate.of(2023, 12, 3), AttendanceStatus.onVacation,
//                   0,0,0,0,0,0,0,0);
//            em.persist(attendance22);
//            Attendance attendance23 = createAttendance(
//                    player1, LocalDate.of(2023, 12, 4), AttendanceStatus.absent,
//                    0,0,0,0,0,0,0,0);
//            em.persist(attendance23);
//            Attendance attendance24 = createAttendance(
//                    player1, LocalDate.of(2023, 12, 5), AttendanceStatus.officiallyExcused,
//                    0,0,0,0,0,0,0,0);
//            em.persist(attendance24);
//            Attendance attendance25 = createAttendance(
//                    player1, LocalDate.of(2023, 12, 6), AttendanceStatus.present,
//                    1, 1, 1, 1, 1, 1, 1, 1);
//            em.persist(attendance25);


        }
        private Attendance createAttendance(
                Player player, LocalDate attendanceDate, AttendanceStatus attendanceStatus, int one,
                int two, int three, int four, int five, int six, int seven, int eight) {
            return new Attendance(player, attendanceDate, attendanceStatus, new Session(one, two, three, four, five, six, seven, eight));
        }

    }
}


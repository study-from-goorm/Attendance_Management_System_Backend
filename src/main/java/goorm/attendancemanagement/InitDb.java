package goorm.attendancemanagement;

import goorm.attendancemanagement.domain.dao.*;
import goorm.attendancemanagement.domain.dto.CreatePlayerDto;
import goorm.attendancemanagement.repository.CourseRepository;
import goorm.attendancemanagement.repository.PlayerRepository;
import goorm.attendancemanagement.service.AdminService;
import goorm.attendancemanagement.service.CourseService;
import goorm.attendancemanagement.service.PlayerService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
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

        private final EntityManager em;
        private final AdminService adminService;
        private final CourseService courseService;
        private final PlayerService playerService;
        private final PlayerRepository playerRepository;

        public void dbInit() {
            adminService.createAdmin("goorm", "1234");

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

            Application application = new Application(playerRepository.findByPlayerEmail("kim@goorm.io"), LocalDate.now().minusDays(2), LocalDate.now(), ApplicationType.휴가, ApplicationStatus.대기, "쉬고싶어요");
            em.persist(application);
            Application application2 = new Application(playerRepository.findByPlayerEmail("lee@goorm.io"), LocalDate.now().minusDays(1), LocalDate.now(), ApplicationType.외출, ApplicationStatus.대기, "놀고올게");
            em.persist(application2);

//
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


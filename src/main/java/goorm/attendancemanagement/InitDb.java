package goorm.attendancemanagement;

import goorm.attendancemanagement.domain.dao.*;
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

        public void dbInit() {
            Admin admin = createAdmin("goorm", "jwjh1205@gmail.com","1234", Role.ADMIN);
            em.persist(admin);

            Course course1 = createCourse("풀스택 1회차");
            em.persist(course1);
            Course course2 = createCourse("풀스택 2회차");
            em.persist(course2);
            Course course3 = createCourse("풀스택 2회차");
            em.persist(course3);

            Player player1 = createPlayer("kim@goorm.io", "123456", "김김김", course1);
            em.persist(player1);
            Player player2 = createPlayer("lee@goorm.io", "234567", "이이이", course2);
            em.persist(player2);
            Player player3 = createPlayer("choi@goorm.io", "345678", "최최최", course3);
            em.persist(player3);
            Player player4 = createPlayer("park@goorm.io", "456789", "박박박", course1);
            em.persist(player4);
            Player player5 = createPlayer("son@goorm.io", "567890", "손손손", course2);
            em.persist(player5);
            Player player6 = createPlayer("kang@goorm.io", "678901", "강강강", course3);
            em.persist(player6);
            Player player7 = createPlayer("hwang@goorm.io", "789012", "황황황", course3);
            em.persist(player7);

            Attendance attendance1 = createAttendance(
                    player1, LocalDate.of(2023, 11, 30), AttendanceStatus.partiallyPresent,
                   1,0, 0, 0, 1, 1, 1, 1);
            em.persist(attendance1);
            Attendance attendance2 = createAttendance(
                    player2, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance2);
            Attendance attendance3 = createAttendance(
                    player3, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance3);
            Attendance attendance4 = createAttendance(
                    player4, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance4);
            Attendance attendance5 = createAttendance(
                    player5, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance5);
            Attendance attendance6 = createAttendance(
                    player6, LocalDate.of(2023, 11, 30), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance6);
            Attendance attendance7 = createAttendance(
                    player7, LocalDate.of(2023, 11, 30), AttendanceStatus.partiallyPresent,
                    1, 1, 0, 0, 1, 1, 1, 1);
            em.persist(attendance7);

            Attendance attendance8 = createAttendance(
                    player1, LocalDate.of(2023, 12, 1), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance8);
            Attendance attendance9 = createAttendance(
                    player2, LocalDate.of(2023, 12, 1), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance9);
            Attendance attendance10 = createAttendance(
                    player3, LocalDate.of(2023, 12, 1), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance10);
            Attendance attendance11 = createAttendance(
                    player4, LocalDate.of(2023, 12, 1), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance11);
            Attendance attendance12 = createAttendance(
                    player5, LocalDate.of(2023, 12, 1), AttendanceStatus.onVacation,
                    0, 0, 0, 0, 0, 0, 0, 0);
            em.persist(attendance12);
            Attendance attendance13 = createAttendance(
                    player6, LocalDate.of(2023, 12, 1), AttendanceStatus.officiallyExcused,
                    0, 0, 0, 0, 0, 0, 0, 0);
            em.persist(attendance13);
            Attendance attendance14 = createAttendance(
                    player7, LocalDate.of(2023, 12, 1), AttendanceStatus.absent,
                    0, 0, 0, 0, 0, 0, 0, 0);
            em.persist(attendance14);

            Attendance attendance15 = createAttendance(
                    player1, LocalDate.of(2023, 12, 2), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance15);
            Attendance attendance16 = createAttendance(
                    player2, LocalDate.of(2023, 12, 2), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance16);
            Attendance attendance17 = createAttendance(
                    player3, LocalDate.of(2023, 12, 2), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance17);
            Attendance attendance18 = createAttendance(
                    player4, LocalDate.of(2023, 12, 2), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance18);
            Attendance attendance19 = createAttendance(
                    player5, LocalDate.of(2023, 12, 2), AttendanceStatus.onVacation,
                    0, 0, 0, 0, 0, 0, 0, 0);
            em.persist(attendance19);
            Attendance attendance20 = createAttendance(
                    player6, LocalDate.of(2023, 12, 2), AttendanceStatus.officiallyExcused,
                    0, 0, 0, 0, 0, 0, 0, 0);
            em.persist(attendance20);
            Attendance attendance21 = createAttendance(
                    player7, LocalDate.of(2023, 12, 2), AttendanceStatus.absent,
                    0, 0, 0, 0, 0, 0, 0, 0);
            em.persist(attendance21);

            Attendance attendance22 = createAttendance(
                    player1, LocalDate.of(2023, 12, 3), AttendanceStatus.onVacation,
                   0,0,0,0,0,0,0,0);
            em.persist(attendance22);
            Attendance attendance23 = createAttendance(
                    player1, LocalDate.of(2023, 12, 4), AttendanceStatus.absent,
                    0,0,0,0,0,0,0,0);
            em.persist(attendance23);
            Attendance attendance24 = createAttendance(
                    player1, LocalDate.of(2023, 12, 5), AttendanceStatus.officiallyExcused,
                    0,0,0,0,0,0,0,0);
            em.persist(attendance24);
            Attendance attendance25 = createAttendance(
                    player1, LocalDate.of(2023, 12, 6), AttendanceStatus.present,
                    1, 1, 1, 1, 1, 1, 1, 1);
            em.persist(attendance25);


        }

        private Attendance createAttendance(
                Player player, LocalDate attendanceDate, AttendanceStatus attendanceStatus, int one,
                int two, int three, int four, int five, int six, int seven, int eight) {
            Attendance attendance = new Attendance();
            attendance.setPlayer(player);
            attendance.setAttendanceDate(attendanceDate);
            attendance.setAttendanceStatus(attendanceStatus);
            attendance.setSession(new Session(one, two, three, four, five, six, seven, eight));
            return attendance;
        }

        private Admin createAdmin(String id, String email, String pw, Role role) {
            Admin admin = new Admin();
            admin.setAdminId(id);
            admin.setAdminEmail(email);
            admin.setAdminPassword(pw);
            admin.setRole(role);
            return admin;
        }

        private Player createPlayer(String email, String pw, String name, Course course) {
            Player player = new Player();
            player.setPlayerEmail(email);
            player.setPlayerPassword(pw);
            player.setPlayerName(name);
            player.setCourse(course);
            return player;
        }

        private Course createCourse(String name) {
            Course course = new Course();
            course.setCourseName(name);
            return course;
        }

    }
}


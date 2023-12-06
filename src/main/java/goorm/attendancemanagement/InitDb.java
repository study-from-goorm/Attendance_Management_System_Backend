package goorm.attendancemanagement;

import goorm.attendancemanagement.domain.dao.Admin;
import goorm.attendancemanagement.domain.dao.Course;
import goorm.attendancemanagement.domain.dao.Player;
import goorm.attendancemanagement.domain.dao.Role;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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


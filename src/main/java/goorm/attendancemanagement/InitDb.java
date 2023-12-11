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
            Admin admin = createAdmin("goorm", "$2a$12$hySKIcaSPZwOGMsb2ENd.uc/RuPH.NrUb6KzNzoNxd/lqGEUGlwvm", Role.ROLE_ADMIN);
            em.persist(admin);

            Course course1 = createCourse("풀스택 1회차");
            em.persist(course1);
            Course course2 = createCourse("풀스택 2회차");
            em.persist(course2);
            Course course3 = createCourse("풀스택 3회차");
            em.persist(course3);

            Player player1 = createPlayer("kim@goorm.io", "$2a$12$4QbpQ6YO8YX/d3O/KQcmYud34CqIg.NtPqRQQXFLyQXyKnJMPJNsu", "김김김", course1, Role.ROLE_PLAYER);
            em.persist(player1);
            Player player2 = createPlayer("lee@goorm.io", "234567", "이이이", course2, Role.ROLE_PLAYER);
            em.persist(player2);
            Player player3 = createPlayer("choi@goorm.io", "345678", "최최최", course3, Role.ROLE_PLAYER);
            em.persist(player3);
            Player player4 = createPlayer("park@goorm.io", "456789", "박박박", course1, Role.ROLE_PLAYER);
            em.persist(player4);
            Player player5 = createPlayer("son@goorm.io", "567890", "손손손", course2, Role.ROLE_PLAYER);
            em.persist(player5);
            Player player6 = createPlayer("kang@goorm.io", "678901", "강강강", course3, Role.ROLE_PLAYER);
            em.persist(player6);
            Player player7 = createPlayer("hwang@goorm.io", "789012", "황황황", course3, Role.ROLE_PLAYER);
            em.persist(player7);

            Application application = new Application(player1, LocalDate.now().minusDays(2), LocalDate.now(), ApplicationType.휴가, ApplicationStatus.대기, "쉬고싶어요");
            em.persist(application);
            Application application2 = new Application(player2, LocalDate.now().minusDays(1), LocalDate.now(), ApplicationType.외출, ApplicationStatus.대기, "놀고올게");
            em.persist(application2);
        }

        private Admin createAdmin(String id, String pw, Role role) {
            return new Admin(id, pw, role);
        }

        private Player createPlayer(String email, String pw, String name, Course course, Role role) {
            return new Player(email, pw, name, course, role);
        }

        private Course createCourse(String name) {
            return new Course(name);
        }

    }
}


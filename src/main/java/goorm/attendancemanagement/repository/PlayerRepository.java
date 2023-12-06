package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Player;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PlayerRepository {

    private final EntityManager em;

    public void save(Player player) {
        em.persist(player);
    }
}

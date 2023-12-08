package goorm.attendancemanagement.repository;

import goorm.attendancemanagement.domain.dao.Admin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AdminRepository {

    private final EntityManager em;

    public void save(Admin admin) {
        em.persist(admin);
    }

    public Admin findAdmin() {
        try {
            return em.createQuery("SELECT a FROM Admin a WHERE a.role = :role", Admin.class)
                    .setParameter("role", "ADMIN")
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}

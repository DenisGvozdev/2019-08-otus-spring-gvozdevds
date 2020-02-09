package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.StatusRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class JpaStatusRepository implements StatusRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Status save(Status status) {
        if (status.getId() <= 0) {
            em.persist(status);
            return status;
        } else {
            return em.merge(status);
        }
    }

    @Override
    public List<Status> findAll() {
        return em.createQuery("select s from Status s", Status.class)
                .getResultList();
    }

    @Override
    public Status findById(long id) {
        return em.find(Status.class, id);
    }

    @Override
    public boolean deleteById(long id) {
        Query query = em.createQuery("delete from Status s where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }

    @Override
    public boolean updateById(Status status) {
        Query query = em.createQuery("update Status s" +
                " set s.name = :name" +
                " where s.id = :id");
        query.setParameter("name", status.getName());
        query.setParameter("id", status.getId());
        query.executeUpdate();
        return true;
    }
}

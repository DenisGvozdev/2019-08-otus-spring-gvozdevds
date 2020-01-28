package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Book;
import ru.gds.spring.interfaces.BookRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book save(Book book) {
        return em.merge(book);
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = em.createEntityGraph("books-entity-graph");
        TypedQuery<Book> query = em.createQuery("select distinct s from Book s ", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book findById(long id) {
        Optional<Book> val = Optional.ofNullable(em.find(Book.class, id));
        return val.orElse(null);
    }

    @Override
    public boolean deleteById(long id) {
        Book book = findById(id);
        if (book != null) {
            em.remove(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateById(Book book) {
        em.merge(book);
        return true;
    }
}

package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Book;
import ru.gds.spring.interfaces.BookRepository;

import javax.persistence.*;
import java.util.List;

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
        TypedQuery<Book> query = em.createQuery("select b from Book b ", Book.class);
        query.setHint("javax.persistence.fetchgraph", entityGraph);
        return query.getResultList();
    }

    @Override
    public Book findById(long id) {
        return em.find(Book.class, id);
    }

    @Override
    public boolean deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id = :bookId");
        query.setParameter("bookId", id);
        query.executeUpdate();
        return true;
    }

    @Override
    public boolean updateById(Book book) {
        em.merge(book);
        return true;
    }
}

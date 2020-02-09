package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Author;
import ru.gds.spring.interfaces.AuthorRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class JpaAuthorRepository implements AuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Author save(Author author) {
        if (author.getId() <= 0) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public List<Author> findAll() {
        return em.createQuery("select a from Author a", Author.class)
                .getResultList();
    }

    @Override
    public Author findById(long id) {
        return em.find(Author.class, id);
    }

    @Override
    public boolean deleteById(long id) {
        Query query = em.createQuery("delete from Author a where a.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }

    @Override
    public boolean updateById(Author author) {
        Query query = em.createQuery("update Author a" +
                " set a.firstName = :firstName," +
                " a.secondName = :secondName," +
                " a.thirdName = :thirdName," +
                " a.birthDate = :birthDate" +
                " where a.id = :id");
        query.setParameter("id", author.getId());
        query.setParameter("firstName", author.getFirstName());
        query.setParameter("secondName", author.getSecondName());
        query.setParameter("thirdName", author.getThirdName());
        query.setParameter("birthDate", author.getBirthDate());
        query.executeUpdate();
        return true;
    }

    @Override
    public List<Author> loadAuthorsByIdString(String authorIds) {
        List<Author> authors = new ArrayList<>();
        String[] authorIdArray = authorIds.split(",");
        if (authorIdArray.length > 0) {
            for (String authorId : authorIdArray) {
                Author author = findById(Long.valueOf(authorId));
                if (author != null) {
                    authors.add(author);
                }
            }
        }
        return authors;
    }
}

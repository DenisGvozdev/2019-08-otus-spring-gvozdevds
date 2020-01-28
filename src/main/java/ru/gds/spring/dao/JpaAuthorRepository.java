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
import java.util.Optional;

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
        return em.createQuery("select s from Author s", Author.class)
                .getResultList();
    }

    @Override
    public Author findById(long id) {
        Optional<Author> val = Optional.ofNullable(em.find(Author.class, id));
        return val.orElse(null);
    }

    @Override
    public boolean deleteById(long id) {
        Query query = em.createQuery("delete" +
                " from Author s" +
                " where s.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }

    @Override
    public boolean updateById(Author author) {
        Query query = em.createQuery("update Author s" +
                " set s.firstName = :firstName," +
                " s.secondName = :secondName," +
                " s.thirdName = :thirdName," +
                " s.birthDate = :birthDate" +
                " where s.id = :id");
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

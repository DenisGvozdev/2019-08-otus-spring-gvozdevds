package ru.gds.spring.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.interfaces.GenreRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Repository
public class JpaGenreRepository implements GenreRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() <= 0) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public Genre findById(long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public boolean deleteById(long id) {
        Query query = em.createQuery("delete from Genre g where g.id = :id");
        query.setParameter("id", id);
        query.executeUpdate();
        return true;
    }

    @Override
    public boolean updateById(Genre genre) {
        Query query = em.createQuery("update Genre g" +
                " set g.name = :name" +
                " where g.id = :id");
        query.setParameter("name", genre.getName());
        query.setParameter("id", genre.getId());
        query.executeUpdate();
        return true;
    }

    @Override
    public List<Genre> loadGenresByIdString(String genreIds) {
        List<Genre> genres = new ArrayList<>();
        String[] genreIdArray = genreIds.split(",");
        if (genreIdArray.length > 0) {
            for (String genreId : genreIdArray) {
                Genre genre = findById(Long.valueOf(genreId));
                if (genre != null) {
                    genres.add(genre);
                }
            }
        }
        return genres;
    }
}

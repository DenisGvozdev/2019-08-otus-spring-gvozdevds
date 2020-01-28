package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Genre;

import java.util.List;

public interface GenreRepository {

    Genre save(Genre genre);

    List<Genre> findAll();

    Genre findById(long id);

    boolean deleteById(long id);

    boolean updateById(Genre genre);

    List<Genre> loadGenresByIdString(String genreIds);
}

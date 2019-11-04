package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Genre;

import java.util.List;

public interface GenreRepository {

    boolean insert(String name);

    List<Genre> getAll();

    Genre getById(long id);

    boolean removeById(long id);

    boolean update(long id, String name);
}

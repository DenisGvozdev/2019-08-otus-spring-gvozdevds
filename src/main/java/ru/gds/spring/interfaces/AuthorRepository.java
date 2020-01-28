package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Author;

import java.util.List;

public interface AuthorRepository {

    Author save(Author author);

    List<Author> findAll();

    Author findById(long id);

    boolean deleteById(long id);

    boolean updateById(Author author);

    List<Author> loadAuthorsByIdString(String authorIds);
}

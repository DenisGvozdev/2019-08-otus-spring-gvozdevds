package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Author;

import java.util.List;

public interface AuthorRepository {

    boolean insert(Author author);

    List<Author> getAll();

    Author getById(long id);

    boolean removeById(long id);

    boolean update(Author author);
}

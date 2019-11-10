package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Book;

import java.util.List;

public interface BookRepository {

    boolean insert(Book book);

    List<Book> getAll();

    Book getById(long id);

    boolean removeById(long id);

    boolean update(Book book);
}

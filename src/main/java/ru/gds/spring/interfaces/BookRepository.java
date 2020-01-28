package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Book;

import java.util.List;

public interface BookRepository {

    Book save(Book book);

    List<Book> findAll();

    Book findById(long id);

    boolean deleteById(long id);

    boolean updateById(Book book);
}

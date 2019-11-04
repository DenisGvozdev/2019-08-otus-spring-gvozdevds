package ru.gds.spring.interfaces;

import ru.gds.spring.domain.Book;

import java.util.Date;
import java.util.List;

public interface BookRepository {

    Boolean insert(
            String name,
            Date createDate,
            String description,
            byte[] image,
            long genre,
            long status,
            long author);

    List<Book> getAll();

    Book getById(long id);

    Boolean removeById(long id);

    Boolean update(
            long id,
            String name,
            Date createDate,
            String description,
            byte[] image,
            long genre,
            long status,
            long author);
}

package ru.gds.spring.microservice.interfaces;

import ru.gds.spring.microservice.domain.Book;
import ru.gds.spring.microservice.dto.BookDto;
import ru.gds.spring.microservice.params.ParamsBook;
import ru.gds.spring.microservice.params.ParamsBookContent;

import java.util.List;

public interface BookService {

    List<BookDto> findAllLight();

    BookDto findById(String id);

    List<BookDto> findByParam(String id, String name);

    BookDto save(ParamsBook params);

    List<BookDto> findAll();

    List<Book> findAllByName(String name);

    String deleteById(String id);

    ParamsBookContent prepareRequestForAddBookContent(ParamsBook params, BookDto bookDto);
}

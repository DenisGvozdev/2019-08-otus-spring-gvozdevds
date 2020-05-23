package ru.gds.spring.microservice.interfaces;

import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.params.ParamsBookContent;


public interface Sender {

    String get(String bookId, int pageStart, int countPages);

    String findFileByBookId(String bookId);

    BookContentDto addUpdateBookContent(ParamsBookContent params);

    void delete(String uri);
}

package ru.gds.spring.interfaces;

import ru.gds.spring.mongo.dto.BookContentDto;
import ru.gds.spring.mongo.dto.PageDto;

import java.util.List;

public interface ContentService {

    List<BookContentDto> getPagesForBooks(List<String> orders);

    BookContentDto getPagesForBook(String order);

    List<PageDto> getPages(String bookId);
}

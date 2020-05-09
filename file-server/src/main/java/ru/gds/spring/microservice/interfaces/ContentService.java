package ru.gds.spring.microservice.interfaces;

import org.springframework.core.io.FileSystemResource;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.params.ParamsBookContent;

import java.io.File;

public interface ContentService {

    BookContentDto getPagesForBook(String bookId, int pageStart, int countPages);

    FileSystemResource findFileByBookId(String bookId);

    BookContentDto save(ParamsBookContent params);

    String deleteByBookId(String id);
}

package ru.gds.spring.microservice.interfaces;

import org.springframework.core.io.FileSystemResource;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.params.ParamsBookContent;

public interface BookContentService {

    BookContentDto getPagesForBook(String bookId, int pageStart, int countPages);

    FileSystemResource findFileByBookId(String bookId);

    BookContentDto save(ParamsBookContent params);

    String deleteByBookId(String id);
}

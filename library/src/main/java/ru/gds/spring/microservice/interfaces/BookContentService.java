package ru.gds.spring.microservice.interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.params.ParamsBookContent;

import java.io.File;

public interface BookContentService {

    BookContentDto getPagesForBook(String bookId, int pageStart, int countPages);

    String findFileByBookId(String bookId);

    BookContentDto save(String bookId, String bookName, String fileType, MultipartFile file);

    BookContentDto addUpdateBookContent(ParamsBookContent params);

    String deleteByBookId(String id);
}

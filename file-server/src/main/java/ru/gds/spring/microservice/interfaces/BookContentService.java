package ru.gds.spring.microservice.interfaces;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;
import ru.gds.spring.microservice.dto.BookContentDto;

public interface BookContentService {

    BookContentDto getPagesForBook(String bookId, int pageStart, int countPages);

    FileSystemResource findFileByBookId(String bookId);

    BookContentDto save(String bookId, String bookName, String fileType, MultipartFile file);

    String deleteByBookId(String id);
}

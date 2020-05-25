package ru.gds.spring.microservice.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.interfaces.BookContentService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping(value = "/content")
public class BookContentController {

    private final BookContentService contentService;

    public BookContentController(BookContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/{bookId}/{pageStart}/{countPages}")
    public BookContentDto findByBookId(
            @RequestParam("bookId") String bookId,
            @RequestParam("pageStart") int pageStart,
            @RequestParam("countPages") int countPages) {
        return contentService.getPagesForBook(bookId, pageStart, countPages);
    }

    @GetMapping("/{bookId}")
    @ResponseBody
    public FileSystemResource findFileByBookId(@RequestParam("bookId") String bookId) {
        return contentService.findFileByBookId(bookId);
    }

    @PostMapping(path = "/{bookId}/{bookName}/{type}/{file}",
            consumes = MULTIPART_FORM_DATA_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public BookContentDto add(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "bookName") String bookName,
            @RequestParam(value = "type") String type,
            @RequestPart(name = "file") MultipartFile file) {
        return contentService.save(bookId, bookName, type, file);
    }

    @DeleteMapping("/{bookId}")
    public String delete(@PathVariable(value = "bookId") String bookId) {
        return contentService.deleteByBookId(bookId);
    }
}

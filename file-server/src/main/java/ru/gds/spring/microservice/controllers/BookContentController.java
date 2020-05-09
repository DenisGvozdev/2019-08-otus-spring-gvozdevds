package ru.gds.spring.microservice.controllers;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.params.ParamsBookContent;
import ru.gds.spring.microservice.interfaces.ContentService;

@RestController
public class BookContentController {

    private final ContentService contentService;

    public BookContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("content")
    public BookContentDto findByBookId(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "pageStart") int pageStart,
            @RequestParam(value = "countPages") int countPages) {
        return contentService.getPagesForBook(bookId, pageStart, countPages);
    }

    @GetMapping("content/bookId")
    @ResponseBody
    public FileSystemResource findFileByBookId(@RequestParam(value = "bookId") String bookId) {
        return contentService.findFileByBookId(bookId);
    }

    @PostMapping("/content")
    public BookContentDto add(ParamsBookContent params) {
        return contentService.save(params);
    }

    @DeleteMapping("content/{bookId}")
    public String delete(@PathVariable(value = "bookId") String bookId) {
        return contentService.deleteByBookId(bookId);
    }
}

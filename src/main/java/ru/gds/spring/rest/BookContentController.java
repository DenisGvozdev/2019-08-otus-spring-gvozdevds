package ru.gds.spring.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gds.spring.mongo.dto.BookContentDto;
import ru.gds.spring.mongo.dto.ParamsDto;
import ru.gds.spring.service.BookContentService;

import java.util.Collection;

@RestController
public class BookContentController {

    private final BookContentService bookContentService;

    public BookContentController(BookContentService bookContentService) {
        this.bookContentService = bookContentService;
    }

    @GetMapping("/api/content")
    public Collection<BookContentDto> getBookContent(ParamsDto params) {
        return bookContentService.getPagesForBooks(params.getOrders());
    }
}

package ru.gds.spring.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gds.spring.interfaces.ContentService;
import ru.gds.spring.mongo.dto.BookContentDto;
import ru.gds.spring.mongo.dto.ParamsDto;

import java.util.Collection;

@RestController
public class BookContentController {

    private final ContentService contentService;

    public BookContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/api/content")
    public Collection<BookContentDto> getBookContent(ParamsDto params) {
        return contentService.getPagesForBooks(params.getOrders());
    }
}

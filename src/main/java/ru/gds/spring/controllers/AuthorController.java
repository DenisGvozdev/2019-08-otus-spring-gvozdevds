package ru.gds.spring.controllers;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.gds.spring.dto.AuthorDto;
import ru.gds.spring.services.AuthorService;

@RestController
public class AuthorController {

    private static final Logger logger = Logger.getLogger(AuthorController.class);

    private final AuthorService authorService;

    AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public Flux<AuthorDto> findAuthorDtoListLight() {
        return authorService.findAllLight();
    }

    @GetMapping("/authors/{bookId}")
    public Flux<AuthorDto> findAuthorDtoListLight(@RequestParam String bookId) {
        return authorService.findAllByBookId(bookId);
    }
}

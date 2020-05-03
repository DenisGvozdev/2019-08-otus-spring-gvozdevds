package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gds.spring.mongo.dto.AuthorDto;
import ru.gds.spring.mongo.services.AuthorService;

import java.util.List;

@RestController
public class AuthorController {

    private final AuthorService authorService;

    AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors")
    public List<AuthorDto> findAuthorDtoListLight() {
        return authorService.findAllLight();
    }

    @GetMapping("/authors/{bookId}")
    public List<AuthorDto> findAuthorDtoListLight(@RequestParam String bookId) {
        return authorService.findAllByBookId(bookId);
    }
}

package ru.gds.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gds.spring.dto.AuthorDto;
import ru.gds.spring.services.AuthorService;

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
    public List<AuthorDto> findAuthorDtoListLight(@RequestParam long bookId) {
        return authorService.findAllByBookId(bookId);
    }
}

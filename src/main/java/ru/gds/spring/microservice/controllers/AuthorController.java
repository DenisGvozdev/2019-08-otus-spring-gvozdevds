package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.*;
import ru.gds.spring.mongo.dto.AuthorDto;
import ru.gds.spring.mongo.params.ParamsAuthor;
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

    @GetMapping("/authors/{authorId}")
    public AuthorDto findById(@RequestParam String authorId) {
        return authorService.findById(authorId);
    }

    @PostMapping("/authors")
    public AuthorDto add(ParamsAuthor params) {
        return authorService.save(params);
    }

    @PutMapping("authors/{id}")
    public AuthorDto update(ParamsAuthor params) {
        return authorService.save(params);
    }

    @DeleteMapping("authors/{id}")
    public String delete(@PathVariable(value = "id") String id) {
        return authorService.deleteById(id);
    }
}

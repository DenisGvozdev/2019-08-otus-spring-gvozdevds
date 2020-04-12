package ru.gds.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.gds.spring.dto.GenreDto;
import ru.gds.spring.services.GenreService;

@RestController
public class GenreController {

    private final GenreService genreService;

    GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public Flux<GenreDto> findAuthorDtoListLight() {
        return genreService.findAllLight();
    }

    @GetMapping("/genres/{bookId}")
    public Flux<GenreDto> findAuthorDtoListLight(@RequestParam String bookId) {
        return genreService.findAllByBookId(bookId);
    }
}

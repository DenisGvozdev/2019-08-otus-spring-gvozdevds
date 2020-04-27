package ru.gds.spring.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gds.spring.dto.GenreDto;
import ru.gds.spring.services.GenreService;

import java.util.List;

@RestController
public class GenreController {

    private final GenreService genreService;

    GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public List<GenreDto> findAuthorDtoListLight() {
        return genreService.findAllLight();
    }

    @GetMapping("/genres/{bookId}")
    public List<GenreDto> findAuthorDtoListLight(@RequestParam String bookId) {
        return genreService.findAllByBookId(bookId);
    }
}

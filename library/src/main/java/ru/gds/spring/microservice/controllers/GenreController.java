package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.*;
import ru.gds.spring.microservice.dto.GenreDto;
import ru.gds.spring.microservice.params.ParamsGenre;
import ru.gds.spring.microservice.services.GenreService;

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

    @GetMapping("/genres/{genreId}")
    public GenreDto findById(@RequestParam String genreId) {
        return genreService.findById(genreId);
    }

    @PostMapping("/genres")
    public GenreDto add(ParamsGenre params) {
        return genreService.save(params);
    }

    @PutMapping("genres/{id}")
    public GenreDto update(ParamsGenre params) {
        return genreService.save(params);
    }

    @DeleteMapping("genres/{id}")
    public String delete(@PathVariable(value = "id") String id) {
        return genreService.deleteById(id);
    }
}

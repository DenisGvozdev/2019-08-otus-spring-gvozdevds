package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.dto.GenreDto;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreReactiveRepository;
import ru.gds.spring.interfaces.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenreService {

    private static final Logger logger = Logger.getLogger(GenreService.class);

    private final GenreReactiveRepository genreReactiveRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    GenreService(GenreReactiveRepository genreReactiveRepository,
                 GenreRepository genreRepository,
                 BookRepository bookRepository) {
        this.genreReactiveRepository = genreReactiveRepository;
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public Mono<Genre> save(Genre genre) {
        return genreReactiveRepository.save(genre);
    }

    public Mono<Genre> findById(String id) {
        return genreReactiveRepository.findById(id);
    }

    public Mono<Void> delete(Genre status) {
        return genreReactiveRepository.delete(status);
    }

    public Mono<Genre> deleteById(String id) {
        return findById(id)
                .flatMap(genre -> delete(genre).then(Mono.just(genre)));
    }

    public Mono<List<GenreDto>> findAllLight() {
        return genreReactiveRepository
                .findAll()
                .map(GenreDto::toDtoLight)
                .collect(Collectors.toList());
    }

    public Mono<List<GenreDto>> findAllByBookId(String bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);

        if (book == null)
            return Mono.empty();

        List<Genre> genresSelected = book.getGenres();

        List<String> genreIds = new ArrayList<>();
        genresSelected.forEach((e) -> genreIds.add(e.getId()));

        return genreReactiveRepository
                .findAll()
                .map(genre -> GenreDto.toDtoWithSelect(genre, genreIds))
                .collect(Collectors.toList());
    }

    public List<Genre> findAllById(List<String> ids) {
        return genreRepository.findAllById(ids);
    }
}

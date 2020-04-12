package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.dto.GenreDto;
import ru.gds.spring.interfaces.BookReactiveRepository;
import ru.gds.spring.interfaces.GenreReactiveRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class GenreService {

    private static final Logger logger = Logger.getLogger(GenreService.class);

    private final GenreReactiveRepository genreReactiveRepository;
    private final BookReactiveRepository bookReactiveRepository;

    GenreService(GenreReactiveRepository genreReactiveRepository, BookReactiveRepository bookReactiveRepository) {
        this.genreReactiveRepository = genreReactiveRepository;
        this.bookReactiveRepository = bookReactiveRepository;
    }

    public Mono<Genre> save(Genre genre) {
        try {
            return genreReactiveRepository.save(genre);
        } catch (Exception e) {
            logger.error("Genre save error: " + Arrays.asList(e.getStackTrace()));
        }
        return Mono.empty();
    }

    public Mono<Genre> findById(String id) {
        try {
            return genreReactiveRepository.findById(id);
        } catch (Exception e) {
            logger.error("Genre not found error: " + Arrays.asList(e.getStackTrace()));
        }
        return Mono.empty();
    }

    public Mono<Void> delete(Genre genre) {
        try {
            return genreReactiveRepository.delete(genre);
        } catch (Exception e) {
            logger.error("Genre not found error: " + Arrays.asList(e.getStackTrace()));
            return Mono.empty();
        }
    }

    public Mono<Void> deleteById(String id) {
        try {
            return findById(id).flatMap(genre -> delete(genre).then(Mono.empty()));
        } catch (Exception e) {
            logger.error("Genre not found error: " + Arrays.asList(e.getStackTrace()));
            return Mono.empty();
        }
    }

    public Flux<GenreDto> findAllLight() {
        try {
            return genreReactiveRepository
                    .findAll()
                    .map(GenreDto::toDtoLight);
        } catch (Exception e) {
            logger.error("Genre not found error: " + Arrays.asList(e.getStackTrace()));
        }
        return Flux.empty();
    }

    public Flux<GenreDto> findAllByBookId(String bookId) {
        try {
            Mono<Book> bookMono = bookReactiveRepository.findById(bookId);
            Flux<Genre> genreFlux = genreReactiveRepository.findAll();
            return genreFlux.flatMap(genre -> bookMono.map(book -> GenreDto.toDtoWithSelect(genre, book.getGenres())));
        } catch (Exception e) {
            logger.error("Genre not found error: " + Arrays.asList(e.getStackTrace()));
            return Flux.empty();
        }
    }

    public Flux<Genre> findAllByIdList(List<String> idList) {
        try {
            return genreReactiveRepository.findAllById(idList);
        } catch (Exception e) {
            logger.error("Genre not found by idList: " + idList + ". Error: " + Arrays.asList(e.getStackTrace()));
            return Flux.empty();
        }
    }

    public Flux<Genre> findAllByName(String name) {
        try {
            return genreReactiveRepository.findAllByName(name);
        } catch (Exception e) {
            logger.error("Genre not found error: " + Arrays.asList(e.getStackTrace()));
            return Flux.empty();
        }
    }
}

package ru.gds.spring.mongo.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.gds.spring.mongo.domain.Book;
import ru.gds.spring.mongo.domain.Genre;
import ru.gds.spring.mongo.dto.GenreDto;
import ru.gds.spring.mongo.interfaces.BookRepository;
import ru.gds.spring.mongo.interfaces.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class GenreService {

    private static final Logger logger = Logger.getLogger(GenreService.class);

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    GenreService(GenreRepository genreRepository, BookRepository bookRepository) {
        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public List<GenreDto> findAllLight() {
        try {
            return genreRepository
                    .findAll()
                    .stream()
                    .map(GenreDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Genres not found Error: " + e.getMessage());
        }
        return new ArrayList<GenreDto>();
    }

    public List<GenreDto> findAllByBookId(String bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);

            if (book == null)
                return new ArrayList<GenreDto>();

            List<Genre> genresSelected = book.getGenres();

            List<String> genreIds = new ArrayList<>();
            genresSelected.forEach((e) -> genreIds.add(e.getId()));

            return genreRepository
                    .findAll()
                    .stream()
                    .map(genre -> GenreDto.toDtoWithSelect(genre, genreIds))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("Genres not found by bookId= " + bookId + " Error: " + e.getMessage());
        }
        return new ArrayList<GenreDto>();
    }

    public List<GenreDto> findAllById(List<String> idList) {
        try {
            return genreRepository
                    .findAllById(idList, null)
                    .stream()
                    .map(GenreDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Genres not found Error: " + e.getMessage());
        }
        return new ArrayList<GenreDto>();
    }
}

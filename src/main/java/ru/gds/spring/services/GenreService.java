package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.dto.GenreDto;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreRepository;

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

    public List<GenreDto> findAllByBookId(long bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);

            if (book == null)
                return new ArrayList<GenreDto>();

            Set<Genre> genresSelected = book.getGenres();

            List<Long> genreIds = new ArrayList<>();
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

    public List<GenreDto> findAllById(List<Long> idList) {
        try {
            return genreRepository
                    .findAllById(idList)
                    .stream()
                    .map(GenreDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Genres not found Error: " + e.getMessage());
        }
        return new ArrayList<GenreDto>();
    }
}

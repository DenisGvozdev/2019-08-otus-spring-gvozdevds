package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.dto.GenreDto;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GenreService {

    private static final Logger logger = Logger.getLogger(GenreService.class);

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    GenreService(GenreRepository genreRepository,
                 BookRepository bookRepository) {

        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public List<GenreDto> findAllLight() {
        return genreRepository
                .findAll()
                .stream()
                .map(GenreDto::toDtoLight)
                .collect(Collectors.toList());
    }

    public List<GenreDto> findAllByBookId(long bookId) {
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
    }

    public List<Genre> findAllById(List<Long> ids) {
        return genreRepository.findAllById(ids);
    }
}

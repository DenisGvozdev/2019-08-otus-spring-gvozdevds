package ru.gds.spring.microservice.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.gds.spring.microservice.domain.Book;
import ru.gds.spring.microservice.domain.Genre;
import ru.gds.spring.microservice.dto.GenreDto;
import ru.gds.spring.microservice.interfaces.BookRepository;
import ru.gds.spring.microservice.interfaces.GenreRepository;
import ru.gds.spring.microservice.interfaces.GenreService;
import ru.gds.spring.microservice.params.ParamsGenre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class GenreServiceImpl implements GenreService {

    private static final Logger logger = Logger.getLogger(GenreServiceImpl.class);

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    GenreServiceImpl(GenreRepository genreRepository, BookRepository bookRepository) {
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
        return new ArrayList<>();
    }

    public List<GenreDto> findAllByBookId(String bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);

            if (book == null)
                return new ArrayList<>();

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
        return new ArrayList<>();
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
        return new ArrayList<>();
    }

    public GenreDto findById(String id) {
        try {
            return GenreDto.toDtoLight(genreRepository.findById(id).orElse(null));
        } catch (Exception e) {
            logger.error("Genre not found Error: " + e.getMessage());
        }
        return null;
    }

    public GenreDto save(ParamsGenre params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getName()))
                throw new Exception("Name is empty");

            if (StringUtils.isEmpty(params.getId())) {
                return GenreDto.toDto(genreRepository.save(new Genre(params.getName())));

            } else {
                Genre genreOld = genreRepository.findById(params.getId()).orElse(null);
                if (genreOld == null)
                    throw new Exception("Genre not found by id = " + params.getId());

                genreOld.setName(params.getName());
                return GenreDto.toDto(genreRepository.save(genreOld));
            }

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
        }
        return new GenreDto();
    }

    public String deleteById(String id) {
        try {
            if (StringUtils.isEmpty(id))
                return "id is empty";

            genreRepository.deleteById(id);
            return "Жанр успешно удален";

        } catch (Exception e) {
            return "Ошибка удаления жанра: " + e.getMessage();
        }
    }
}

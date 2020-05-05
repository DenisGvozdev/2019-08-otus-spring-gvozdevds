package ru.gds.spring.mongo.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.gds.spring.mongo.domain.Book;
import ru.gds.spring.mongo.domain.Genre;
import ru.gds.spring.mongo.dto.GenreDto;
import ru.gds.spring.mongo.interfaces.BookRepository;
import ru.gds.spring.mongo.interfaces.GenreRepository;
import ru.gds.spring.mongo.params.ParamsGenre;

import java.util.ArrayList;
import java.util.List;
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

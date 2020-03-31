package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.dto.BookDto;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.interfaces.StatusRepository;
import ru.gds.spring.params.ParamsBook;
import ru.gds.spring.util.CommonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;

    BookService(BookRepository bookRepository,
                GenreRepository genreRepository,
                AuthorRepository authorRepository,
                StatusRepository statusRepository) {

        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.statusRepository = statusRepository;
    }

    public List<BookDto> findAllLight() {
        try {
            return bookRepository
                    .findAll()
                    .stream()
                    .map(BookDto::toDtoLight)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Books not found error:" + e.getMessage());
            return new ArrayList<BookDto>();
        }
    }

    public List<BookDto> findByParam(String idString, String name) {
        try {
            Long id = (StringUtils.isEmpty(idString) || "undefined".equals(idString))
                    ? null : Long.valueOf(idString);

            if (id != null) {
                Book book = bookRepository.findById(id).orElse(null);
                return BookDto.toListDtoWithImage(book);

            } else if (!StringUtils.isEmpty(name)) {
                return bookRepository
                        .findByNameContainingIgnoreCase(name)
                        .stream()
                        .map(BookDto::toDto)
                        .collect(Collectors.toList());
            }

        } catch (Exception e) {
            logger.error("Book not found by name like %" + name + "% error: " + e.getMessage());
        }
        return new ArrayList<BookDto>();
    }

    public Book save(ParamsBook params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getName()))
                throw new Exception("Book name is empty");

            List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToArrayList(params.getGenreIds()));
            List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToArrayList(params.getAuthorIds()));

            Status status = statusRepository.findById(params.getStatusId()).orElse(null);
            byte[] image = (params.getFile() != null) ? params.getFile().getBytes() : null;

            checkStatusGenresAuthors(status, genres, authors);

            Book book;
            if (params.getIdLong() == null) {
                book = new Book(
                        params.getName(),
                        new Date(),
                        params.getDescription(),
                        image,
                        new HashSet<Genre>(genres),
                        new HashSet<Author>(authors),
                        status);
            } else {
                book = bookRepository.findById(params.getIdLong()).orElse(null);
                if (book == null)
                    throw new Exception("Book not found");

                book.setName(params.getName());
                book.setDescription(params.getDescription());
                book.setGenres(new HashSet<>(genres));
                book.setAuthors(new HashSet<>(authors));
                book.setStatus(status);
                book.setImage(image);
            }
            return bookRepository.save(book);

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
            return null;
        }
    }

    public String delete(String idString) {
        try {
            Long id = CommonUtils.stringToLong(idString);
            if (id == null)
                throw new Exception("Book id is null");

            bookRepository.deleteById(id);
            return "Книга успешно удалена";

        } catch (Exception e) {
            return "Ошибка удаления книги: " + e.getMessage();
        }
    }

    private static void checkStatusGenresAuthors(Status status, List<Genre> genres, List<Author> authors) throws Exception {
        if (status == null)
            throw new Exception("Status not found");

        if (genres == null || genres.isEmpty())
            throw new Exception("Genre not found");

        if (authors == null || authors.isEmpty())
            throw new Exception("Author not found");
    }
}

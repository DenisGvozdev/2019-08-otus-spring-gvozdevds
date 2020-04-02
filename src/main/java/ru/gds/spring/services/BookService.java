package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.dto.BookDto;
import ru.gds.spring.interfaces.*;
import ru.gds.spring.params.ParamsBook;
import ru.gds.spring.util.CommonUtils;
import ru.gds.spring.util.FileUtils;

import java.util.*;

@Component
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class);

    private final BookReactiveRepository bookReactiveRepository;
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;

    BookService(BookReactiveRepository bookReactiveRepository,
                BookRepository bookRepository,
                GenreRepository genreRepository,
                AuthorRepository authorRepository,
                StatusRepository statusRepository) {
        this.bookReactiveRepository = bookReactiveRepository;
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.statusRepository = statusRepository;
    }

    public Flux<BookDto> findAllLight() {
        try {
            return bookReactiveRepository
                    .findAll()
                    .map(BookDto::toDtoLight);
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return Flux.empty();
    }

    public Mono<Book> getById(String id) {
        try {
            return bookReactiveRepository.findById(id);
        } catch (Exception e) {
            logger.error("Book not found by id= " + id + ". Error: " + e.getMessage());
        }
        return Mono.empty();
    }

    public Flux<BookDto> findByParam(String id, String name) {
        try {
            if (!StringUtils.isEmpty(id) && !"undefined".equals(id)) {
                return findAllById(id);

            } else if (!StringUtils.isEmpty(name)) {
                return findByNameLight(name);
            }
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return Flux.empty();
    }

    public Mono<BookDto> save(ParamsBook params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getName()))
                throw new Exception("Book name is empty");

            List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToListString(params.getGenreIds()));
            List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToListString(params.getAuthorIds()));

            Status status = statusRepository.findById(params.getStatusId()).orElse(null);
            byte[] image = FileUtils.convertFilePartToByteArray(params.getFile());

            checkStatusGenresAuthors(status, genres, authors);

            Book book;
            if (StringUtils.isEmpty(params.getId()) || "undefined".equals(params.getId())) {
                book = new Book(
                        params.getName(),
                        new Date(),
                        params.getDescription(),
                        image,
                        new ArrayList<Genre>(genres),
                        new ArrayList<Author>(authors),
                        status);
            } else {
                book = bookRepository.findById(params.getId()).orElse(null);
                if (book == null)
                    throw new Exception("Book not found");

                book.setName(params.getName());
                book.setDescription(params.getDescription());
                book.setGenres(genres);
                book.setAuthors(authors);
                book.setStatus(status);
                book.setImage((image != null) ? image : book.getImage());
            }
            return bookReactiveRepository.save(book).map(BookDto::toDto);

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
        }
        return Mono.empty();
    }

    public Mono<Book> deleteById(String id) {
        try {
            return getById(id)
                    .flatMap(book -> delete(book).then(Mono.just(book)));

        } catch (Exception e) {
            logger.error("Book not found");
        }
        return Mono.empty();
    }

    public Mono<Void> delete(Book book) {
        return bookReactiveRepository.delete(book);
    }

    private Flux<BookDto> findByNameLight(String name) {
        return bookReactiveRepository
                .findByNameContainingIgnoreCase(name)
                .map(BookDto::toDtoLight);
    }

    private Flux<BookDto> findAllById(String id) {
        try {
            List<String> idList = new ArrayList<>();
            idList.add(id);
            return bookReactiveRepository
                    .findAllById(idList)
                    .map(BookDto::toDto);
        } catch (Exception e) {
            logger.error("Book not found by id= " + id + ". Error: " + e.getMessage());
        }
        return Flux.empty();
    }

    public List<Book> findAll() {
        try {
            return bookRepository.findAll();
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return new ArrayList<>();
    }

    public Book findById(String id) {
        try {
            return bookRepository.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return null;
    }

    public List<Book> findAllByName(String name) {
        try {
            return bookRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return new ArrayList<>();
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

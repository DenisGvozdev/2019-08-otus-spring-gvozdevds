package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
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

@Service
public class BookService {

    private static final Logger logger = Logger.getLogger(BookService.class);

    private final BookReactiveRepository bookReactiveRepository;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final StatusService statusService;

    BookService(BookReactiveRepository bookReactiveRepository,
                GenreService genreService,
                AuthorService authorService,
                StatusService statusServicey) {

        this.bookReactiveRepository = bookReactiveRepository;
        this.genreService = genreService;
        this.authorService = authorService;
        this.statusService = statusServicey;
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

    public Flux<BookDto> findByParam(ParamsBook params) {
        try {
            if (!StringUtils.isEmpty(params.getId()) && !"undefined".equals(params.getId())) {
                return findAllById(params.getId());

            } else if (!StringUtils.isEmpty(params.getName())) {
                return findByNameLight(params.getName());
            }
        } catch (Exception e) {
            logger.error("Book not found");
        }
        return Flux.empty();
    }

    public Mono<Book> save(ParamsBook params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getName()))
                throw new Exception("Book name is empty");

            Flux<Genre> genreFlux = genreService.findAllByIdList(CommonUtils.convertStringToListString(params.getGenreIds()));
            Flux<Author> authorFlux = authorService.findAllByIdList(CommonUtils.convertStringToListString(params.getAuthorIds()));
            Flux<Status> statusFlux = statusService.findStatusByIdList(CommonUtils.convertStringToListString(params.getStatusId()));
            byte[] image = FileUtils.convertFilePartToByteArray(params.getFile());
            String id = (!StringUtils.isEmpty(params.getId()) && !"undefined".equals(params.getId())) ? params.getId() : null;

            Flux<Book> bookFlux = Flux.just(new Book(
                    id,
                    params.getName(),
                    new Date(),
                    params.getDescription(),
                    image,
                    new ArrayList<Genre>(),
                    new ArrayList<Author>(),
                    null));

            if (genreFlux == null || authorFlux == null || statusFlux == null) {
                logger.debug("skip save because genreFlux or authorFlux or statusFlux is null");
                return Mono.empty();
            }

            return bookFlux.flatMap(book -> genreFlux.map(genre -> addGenre(book, genre)))
                    .flatMap(book -> authorFlux.map(author -> addAuthor(book, author)))
                    .flatMap(book -> statusFlux.map(status -> setStatus(book, status)))
                    .flatMap(bookReactiveRepository::save)
                    .next();

        } catch (Exception e) {
            logger.error("Error add book: " + Arrays.asList(e.getStackTrace()));
            return Mono.empty();
        }
    }

    private Book addGenre(Book book, Genre genre) {
        book.getGenres().add(genre);
        return book;
    }

    private Book addAuthor(Book book, Author author) {
        book.getAuthors().add(author);
        return book;
    }

    private Book setStatus(Book book, Status status) {
        book.setStatus(status);
        return book;
    }

    public Mono<Void> deleteById(String id) {
        try {
            return getById(id)
                    .flatMap(book -> delete(book).then(Mono.empty()));

        } catch (Exception e) {
            logger.error("Book not found. Error: " + Arrays.asList(e.getStackTrace()));
            return Mono.empty();
        }
    }

    public Mono<Void> delete(Book book) {
        try {
            return bookReactiveRepository.delete(book);
        } catch (Exception e) {
            logger.error("Book not found error: " + Arrays.asList(e.getStackTrace()));
            return Mono.empty();
        }
    }

    private Flux<BookDto> findByNameLight(String name) {
        try {
            return bookReactiveRepository
                    .findByNameContainingIgnoreCase(name)
                    .map(BookDto::toDtoLight);
        } catch (Exception e) {
            logger.error("Book not found by name= " + name + ". Error: " + Arrays.asList(e.getStackTrace()));
        }
        return Flux.empty();
    }

    private Flux<BookDto> findAllById(String id) {
        try {
            List<String> idList = new ArrayList<>();
            idList.add(id);
            return bookReactiveRepository
                    .findAllById(idList)
                    .map(BookDto::toDtoWithImage);
        } catch (Exception e) {
            logger.error("Book not found by id= " + id + ". Error: " + Arrays.asList(e.getStackTrace()));
        }
        return Flux.empty();
    }

    public Flux<Book> findAllByName(String name) {
        try {
            return bookReactiveRepository.findByNameContainingIgnoreCase(name);
        } catch (Exception e) {
            logger.error("Book not found. Error: " + Arrays.asList(e.getStackTrace()));
        }
        return Flux.empty();
    }
}

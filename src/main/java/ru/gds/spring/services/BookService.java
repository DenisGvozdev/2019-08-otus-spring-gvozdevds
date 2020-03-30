package ru.gds.spring.services;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
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
import java.util.stream.Collectors;

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

    public Mono<List<BookDto>> findAllLight() {
        return bookReactiveRepository
                .findAll()
                .map(BookDto::toDtoLight)
                .collect(Collectors.toList());
    }

    public Mono<List<BookDto>> findByNameLight(String name) {
        return bookReactiveRepository
                .findByNameContainingIgnoreCase(name)
                .map(BookDto::toDtoLight)
                .collect(Collectors.toList());
    }

    public Mono<List<BookDto>> findById(String id) {
        return bookReactiveRepository
                .findById(id)
                .map(BookDto::toListDto);
    }

    public Mono<Book> getById(String id) {
        return bookReactiveRepository
                .findById(id);
    }

    public Mono<Book> save(Book book) {
        return bookReactiveRepository.save(book);
    }

    public Mono<Book> save(ParamsBook params) throws Exception {

        List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToListString(params.getGenreIds()));
        List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToListString(params.getAuthorIds()));
        ;
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
            book.setImage(image);
        }
        return bookReactiveRepository.save(book);
    }

    public Mono<Void> delete(Book book) {
        return bookReactiveRepository.delete(book);
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

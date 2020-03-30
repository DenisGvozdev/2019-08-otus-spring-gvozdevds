package ru.gds.spring.controllers;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import ru.gds.spring.dto.BookDto;
import ru.gds.spring.params.ParamsBook;
import ru.gds.spring.services.BookService;

import java.util.List;

@RestController
public class BookController {

    private static final Logger logger = Logger.getLogger(BookController.class);

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books")
    @ResponseStatus(HttpStatus.OK)
    public Mono<List<BookDto>> findBookDtoListLight() {
        return bookService.findAllLight();
    }

    @GetMapping("books/{param}")
    public Mono<List<BookDto>> getBookByName(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "name") String name) {

        if (!StringUtils.isEmpty(bookId)) {
            return bookService.findById(bookId);

        } else if (!StringUtils.isEmpty(name)) {
            return bookService.findByNameLight(name);
        }
        return Mono.empty();
    }

    @PostMapping(value = "books", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<BookDto> addBook(ParamsBook params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getName()))
                throw new Exception("Book name is empty");

            return bookService.save(params).map(BookDto::toDto);

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());
            return null;
        }
    }

    @PutMapping(value = "books/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<BookDto> updateBook(ParamsBook params) {
        try {
            if (params == null)
                throw new Exception("Input params is empty");

            if (StringUtils.isEmpty(params.getName()))
                throw new Exception("Book name is empty");

            return bookService.save(params).map(BookDto::toDto);

        } catch (Exception e) {
            logger.error("Error update book with id = " + params.getId() + " : " + e.getMessage());
            return null;
        }
    }

    @DeleteMapping("books/{bookId}")
    public Mono<ResponseEntity<Void>> removeBookById(@PathVariable(value = "bookId") String bookId) {
        return bookService.getById(bookId)
                .flatMap(selectedBook ->
                        bookService.delete(selectedBook)
                                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

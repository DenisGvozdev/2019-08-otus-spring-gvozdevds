package ru.gds.spring.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.gds.spring.domain.Book;
import ru.gds.spring.dto.BookDto;
import ru.gds.spring.params.ParamsBook;
import ru.gds.spring.services.BookService;

@RestController
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookDto> findBookDtoListLight() {
        return bookService.findAllLight();
    }

    @GetMapping("books/{params}")
    public Flux<BookDto> getBookByName(ParamsBook params) {
        return bookService.findByParam(params);
    }

    @PostMapping(value = "/books",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> addBook(ParamsBook params) {
        return bookService.save(params);
    }

    @PutMapping(value = "books/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> updateBook(ParamsBook params) {
        return bookService.save(params);
    }

    @DeleteMapping("books/{bookId}")
    public Mono<Void> removeBookById(@PathVariable(value = "bookId") String bookId) {
        return bookService.deleteById(bookId);
    }
}

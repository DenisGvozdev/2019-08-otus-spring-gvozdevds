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

    @GetMapping("books/{param}")
    public Flux<BookDto> getBookByName(
            @RequestParam(value = "bookId") String bookId,
            @RequestParam(value = "name") String name) {
        return bookService.findByParam(bookId, name);
    }

    @PostMapping(value = "/books",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BookDto> addBook(ParamsBook params) {
        return bookService.save(params);
    }

    @PutMapping(value = "books/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<BookDto> updateBook(ParamsBook params) {
        return bookService.save(params);
    }

    @DeleteMapping("books/{bookId}")
    public Mono<Book> removeBookById(@PathVariable(value = "bookId") String bookId) {
        return bookService.deleteById(bookId);
    }
}

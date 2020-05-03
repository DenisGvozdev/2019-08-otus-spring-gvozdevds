package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.*;
import ru.gds.spring.mongo.dto.BookDto;
import ru.gds.spring.mongo.params.ParamsBook;
import ru.gds.spring.mongo.services.BookService;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books")
    public List<BookDto> getAll() {
        return bookService.findAllLight();
    }

    @GetMapping("books/{param}")
    public List<BookDto> findByParam(
            @RequestParam(value = "bookId") String id,
            @RequestParam(value = "name") String name) {
        return bookService.findByParam(id, name);
    }

    @PostMapping("/books")
    public BookDto add(ParamsBook params) {
        return bookService.save(params);
    }

    @PutMapping("books/{id}")
    public BookDto update(ParamsBook params) {
        return bookService.save(params);
    }

    @DeleteMapping("books/{bookId}")
    public String delete(@PathVariable(value = "bookId") String bookId) {
        return bookService.deleteById(bookId);
    }
}

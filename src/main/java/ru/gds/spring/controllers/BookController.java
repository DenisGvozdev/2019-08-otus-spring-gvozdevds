package ru.gds.spring.controllers;

import org.springframework.web.bind.annotation.*;
import ru.gds.spring.domain.Book;
import ru.gds.spring.dto.BookDto;
import ru.gds.spring.params.ParamsBook;
import ru.gds.spring.services.BookService;

import java.util.*;

@RestController
public class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("books")
    public List<BookDto> getAllBooks() {
        return bookService.findAllLight();
    }

    @GetMapping("books/{param}")
    public List<BookDto> findBookByParam(
            @RequestParam(value = "bookId") String id,
            @RequestParam(value = "name") String name) {
        return bookService.findByParam(id, name);
    }

    @PostMapping("/books")
    public BookDto addBook(ParamsBook params) {
        Book book = bookService.save(params);
        return BookDto.toDto(book);
    }

    @PutMapping("books/{id}")
    public BookDto updateBook(ParamsBook params) {
        Book book = bookService.save(params);
        return BookDto.toDto(book);
    }

    @DeleteMapping("books/{bookId}")
    public String removeBookById(@RequestParam(value = "bookId") String id) {
        return bookService.delete(id);
    }
}

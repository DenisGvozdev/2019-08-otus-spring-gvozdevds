package ru.gds.spring.microservice.controllers;

import org.springframework.web.bind.annotation.*;
import ru.gds.spring.microservice.dto.BookContentDto;
import ru.gds.spring.microservice.dto.BookDto;
import ru.gds.spring.microservice.interfaces.BookService;
import ru.gds.spring.microservice.interfaces.Sender;
import ru.gds.spring.microservice.params.ParamsBook;
import ru.gds.spring.microservice.params.ParamsBookContent;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;
    private final Sender sender;

    BookController(BookService bookService, Sender sender) {
        this.bookService = bookService;
        this.sender = sender;
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
        BookDto bookDto = bookService.save(params);

        ParamsBookContent reqParams = bookService.prepareRequestForAddBookContent(params, bookDto);
        BookContentDto saveFilesResult = sender.addUpdateBookContent(reqParams);
        bookDto.setStatus(saveFilesResult.getStatus());
        bookDto.setMessage(saveFilesResult.getMessage());
        return bookDto;
    }

    @PutMapping("books/{id}")
    public BookDto update(ParamsBook params) {
        BookDto bookDto = bookService.save(params);
        ParamsBookContent reqParams = bookService.prepareRequestForAddBookContent(params, bookDto);
        BookContentDto saveFilesResult = sender.addUpdateBookContent(reqParams);
        bookDto.setStatus(saveFilesResult.getStatus());
        bookDto.setMessage(saveFilesResult.getMessage());
        return bookDto;
    }

    @DeleteMapping("books/{bookId}")
    public String delete(@PathVariable(value = "bookId") String bookId) {
        return bookService.deleteById(bookId);
    }
}

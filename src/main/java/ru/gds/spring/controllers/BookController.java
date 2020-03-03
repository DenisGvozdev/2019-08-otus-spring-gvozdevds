package ru.gds.spring.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.*;
import ru.gds.spring.util.CommonUtils;
import ru.gds.spring.util.FileUtils;

import java.io.File;
import java.util.*;

import org.apache.log4j.Logger;

@Controller
public class BookController {

    private static final Logger logger = Logger.getLogger(BookController.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;
    private final CommentRepository commentRepository;

    BookController(
            BookRepository br,
            GenreRepository gr,
            AuthorRepository ar,
            StatusRepository sr,
            CommentRepository cr) {
        bookRepository = br;
        genreRepository = gr;
        authorRepository = ar;
        statusRepository = sr;
        commentRepository = cr;
    }

    @GetMapping("/hello")
    public String listPage(Model model) {
        return "helloPage";
    }

    @GetMapping("/book/all")
    public String findAllBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "index";
    }

    @GetMapping("/book/one")
    public String findBookById(@RequestParam Long bookId, Model model) {
        Book book = bookRepository.findById(bookId).get();
        model.addAttribute("books", new ArrayList<Book>(Arrays.asList(book)));
        return "index";
    }

    @GetMapping("/book/add")
    public String addBook(String name, String description, String imagePath,
                          long statusId, String genreIds, String authorIds, Model model) {

        Status status = statusRepository.findById(statusId).get();
        List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToArrayList(genreIds));
        List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToArrayList(authorIds));
        File image = FileUtils.getFile(imagePath);

        Book book = new Book(
                name,
                new Date(),
                description,
                FileUtils.convertFileToByteArray(image),
                new HashSet<>(genres),
                new HashSet<>(authors),
                status);

        book = bookRepository.save(book);
        long id = book.getId();
        logger.debug((id > 0) ? "create book successful" : "create book error");

        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "index";
    }
}

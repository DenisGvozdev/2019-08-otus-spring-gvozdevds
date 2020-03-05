package ru.gds.spring.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/all")
    public String findAllBooks(Model model) {
        List<Book> bookList = bookRepository.findAll();
        model.addAttribute("books", bookList);
        return "index";
    }

    @GetMapping("/one")
    public String findBookById(@RequestParam Long bookId, Model model) {
        List<Book> bookList = new ArrayList<>();
        try {
            Book book = bookRepository.findById(bookId).get();
            bookList.add(book);

        } catch (Exception e) {
            logger.debug("Book not found by id = " + bookId);

        } finally {
            model.addAttribute("books", bookList);
        }
        return "index";
    }

    @PostMapping("/add")
    public String addBook(String name, String description, String imagePath,
                          long statusId, String genreIds, String authorIds, Model model) {
        try {

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

        } catch (Exception e) {
            logger.debug("Error add book: " + e.getMessage());

        } finally {
            List<Book> bookList = bookRepository.findAll();
            model.addAttribute("books", bookList);
        }
        return "index";
    }

    @PostMapping("/update")
    public String updateBook(long bookId, String name, String description, String imagePath,
                             long statusId, String genreIds, String authorIds, Model model) {
        try {
            List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToArrayList(genreIds));
            List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToArrayList(authorIds));
            Status status = statusRepository.findById(statusId).get();
            File image = FileUtils.getFile(imagePath);

            Book book = bookRepository.findById(bookId).get();
            book.setName(name);
            book.setDescription(description);
            book.setGenres(new HashSet<>(genres));
            book.setAuthors(new HashSet<>(authors));
            book.setStatus(status);
            book.setImage(FileUtils.convertFileToByteArray(image));

            bookRepository.save(book);
            logger.debug("book successful updated");

        } catch (Exception e) {
            logger.debug("Error update book with id = " + bookId + " : " + e.getMessage());

        } finally {
            List<Book> books = bookRepository.findAll();
            model.addAttribute("books", books);
        }
        return "index";
    }

    @PostMapping("/remove")
    public String removeBookById(@RequestParam Long bookId, Model model) {
        try {
            bookRepository.deleteById(bookId);
            logger.debug("book successful deleted");
        } catch (Exception e) {
            logger.debug("Book not found by id = " + bookId);
        } finally {
            List<Book> bookList = bookRepository.findAll();
            model.addAttribute("books", bookList);
        }
        return "index";
    }
}

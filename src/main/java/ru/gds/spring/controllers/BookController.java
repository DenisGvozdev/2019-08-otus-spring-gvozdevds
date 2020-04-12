package ru.gds.spring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.gds.spring.dto.BookDto;
import ru.gds.spring.params.ParamsBook;
import ru.gds.spring.services.AuthorService;
import ru.gds.spring.services.BookService;
import ru.gds.spring.services.GenreService;
import ru.gds.spring.services.StatusService;
import ru.gds.spring.util.CommonUtils;

@Controller
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final StatusService statusService;

    BookController(BookService bookService,
                   AuthorService authorService,
                   GenreService genreService,
                   StatusService statusService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.genreService = genreService;
        this.statusService = statusService;
    }

    @GetMapping("/")
    public String findAllBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("user", CommonUtils.getCurrentUser());
        return "index";
    }

    @GetMapping("/find")
    public String findBookById(@RequestParam String name, Model model) {
        model.addAttribute("books", bookService.findByParam(null, name));
        model.addAttribute("user", CommonUtils.getCurrentUser());
        return "index";
    }

    @PostMapping("/add")
    public String addBook(ParamsBook params, ModelMap model) {
        bookService.save(params);
        model.addAttribute("books", bookService.findAllLight());
        model.addAttribute("user", CommonUtils.getCurrentUser());
        return "index";
    }

    @PostMapping("/edit")
    public String updateBook(ParamsBook params, ModelMap model) {
        bookService.save(params);
        model.addAttribute("books", bookService.findAllLight());
        model.addAttribute("user", CommonUtils.getCurrentUser());
        return "index";
    }

    @GetMapping("/remove")
    public String removeBookById(@RequestParam Long bookId, Model model) {
        bookService.deleteById(bookId);
        model.addAttribute("books", bookService.findAllLight());
        model.addAttribute("user", CommonUtils.getCurrentUser());
        return "index";
    }

    @GetMapping("/info")
    public String getInfo(Model model) {
        BookDto bookDto = new BookDto();
        bookDto.setAuthors(authorService.findAllLight());
        bookDto.setGenres(genreService.findAllLight());
        bookDto.setStatuses(statusService.findAllLight());

        model.addAttribute("show", false);
        model.addAttribute("operation", "add");
        model.addAttribute("book", bookDto);
        return "formAddUpdateBook";
    }

    @GetMapping("/infoBook")
    public String getInfoBook(@RequestParam long bookId, @RequestParam String mode, Model model) {
        model.addAttribute("show", "show".equalsIgnoreCase(mode));
        model.addAttribute("operation", "edit");
        model.addAttribute("book", bookService.findById(bookId));
        return "/formAddUpdateBook";
    }
}

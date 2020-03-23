package ru.gds.spring.controllers;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.dto.AuthorDto;
import ru.gds.spring.dto.BookDto;
import ru.gds.spring.dto.GenreDto;
import ru.gds.spring.dto.StatusDto;
import ru.gds.spring.interfaces.*;
import ru.gds.spring.util.CommonUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
public class BookController {

    private static final Logger logger = Logger.getLogger(BookController.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;

    BookController(
            BookRepository bookRepository,
            GenreRepository genreRepository,
            AuthorRepository authorRepository,
            StatusRepository statusRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.statusRepository = statusRepository;
    }

    @GetMapping("/")
    public String findAllBooks(Model model) {
        List<Book> bookList = bookRepository.findAll();
        model.addAttribute("books", bookList);
        return "index";
    }

    @GetMapping("/find")
    public String findBookById(
            @RequestParam String name,
            Model model) {

        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookRepository.findByNameContainingIgnoreCase(name);

        } catch (Exception e) {
            logger.error("Book not found by name like %" + name + "%");

        } finally {
            model.addAttribute("books", bookList);
        }
        return "index";
    }

    @PostMapping("/add")
    public String addBook(
            @RequestParam MultipartFile file,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam long statusId,
            @RequestParam String genreIds,
            @RequestParam String authorIds,
            ModelMap model) {
        try {

            if (StringUtils.isEmpty(name))
                throw new Exception("Book name is empty");

            Status status = statusRepository.findById(statusId).orElse(null);
            List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToArrayList(genreIds));
            List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToArrayList(authorIds));
            byte[] image = file.getBytes();

            checkStatusGenresAuthors(status, genres, authors);

            Book book = new Book(
                    name,
                    new Date(),
                    description,
                    image,
                    new HashSet<>(genres),
                    new HashSet<>(authors),
                    status);
            bookRepository.save(book);
            logger.debug("book successful saved");

        } catch (Exception e) {
            logger.error("Error add book: " + e.getMessage());

        } finally {
            List<Book> bookList = bookRepository.findAll();
            model.addAttribute("books", (bookList == null) ? new ArrayList<Book>() : bookList);
        }
        return "redirect:/";
    }

    @PostMapping("/createUpdate")
    public String updateBook(
            @RequestParam MultipartFile file,
            @RequestParam long bookId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam long statusId,
            @RequestParam String genreIds,
            @RequestParam String authorIds,
            Model model) {
        try {

            if (StringUtils.isEmpty(name))
                throw new Exception("Book name is empty");

            List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToArrayList(genreIds));
            List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToArrayList(authorIds));
            Status status = statusRepository.findById(statusId).orElse(null);
            byte[] image = file.getBytes();

            checkStatusGenresAuthors(status, genres, authors);

            Book book = bookRepository.findById(bookId).orElse(null);
            if (book == null)
                throw new Exception("Book not found");

            book.setName(name);
            book.setDescription(description);
            book.setGenres(new HashSet<>(genres));
            book.setAuthors(new HashSet<>(authors));
            book.setStatus(status);
            book.setImage(image);

            bookRepository.save(book);
            logger.debug("book successful updated");

        } catch (Exception e) {
            logger.error("Error update book with id = " + bookId + " : " + e.getMessage());

        } finally {
            List<Book> books = bookRepository.findAll();
            model.addAttribute("books", books);
        }
        return "redirect:/";
    }

    @GetMapping("/remove")
    public String removeBookById(
            @RequestParam Long bookId,
            Model model) {
        try {
            bookRepository.deleteById(bookId);
            logger.debug("book successful deleted");

        } catch (Exception e) {
            logger.error("Book not found by id = " + bookId);

        } finally {
            List<Book> bookList = bookRepository.findAll();
            model.addAttribute("books", bookList);
        }
        return "index";
    }

    @GetMapping("/info")
    public String getInfo(Model model) {
        BookDto bookDto = new BookDto();
        try {
            List<Author> authorEntityList = authorRepository.findAll();
            List<Genre> genreEntityList = genreRepository.findAll();
            List<Status> statusEntityList = statusRepository.findAll();

            List<AuthorDto> authorDtoList = new ArrayList<AuthorDto>();
            List<GenreDto> genreDtoList = new ArrayList<GenreDto>();
            List<StatusDto> statusDtoList = new ArrayList<StatusDto>();
            prepareListDto(authorEntityList, genreEntityList, statusEntityList,
                    authorDtoList, genreDtoList, statusDtoList);

            bookDto.setAuthors(authorDtoList);
            bookDto.setGenres(genreDtoList);
            bookDto.setStatuses(statusDtoList);

        } catch (Exception e) {
            logger.error("Error get info ");

        } finally {
            model.addAttribute("show", false);
            model.addAttribute("operation", "add");
            model.addAttribute("book", bookDto);
        }
        return "formAddUpdateBook";
    }

    @GetMapping("/infoBook")
    public String getInfoBook(
            @RequestParam Long bookId,
            @RequestParam String mode,
            Model model) {

        BookDto bookDto = new BookDto();
        try {
            bookDto = prepareBookDto(bookId);

        } catch (Exception e) {
            logger.error("Error get book info by id = " + bookId);

        } finally {
            model.addAttribute("show", "show".equalsIgnoreCase(mode));
            model.addAttribute("operation", "createUpdate");
            model.addAttribute("book", bookDto);
        }
        return "/formAddUpdateBook";
    }

    private void prepareListDto(
            List<Author> authorEntityList,
            List<Genre> genreEntityList,
            List<Status> statusEntityList,
            List<AuthorDto> authorDtoList,
            List<GenreDto> genreDtoList,
            List<StatusDto> statusDtoList) {

        for (Author author : authorEntityList) {
            AuthorDto authorDto = new AuthorDto(
                    author.getId(),
                    author.getFirstName(),
                    author.getSecondName(),
                    author.getThirdName(),
                    author.getBirthDate(),
                    false
            );
            authorDtoList.add(authorDto);
        }

        for (Genre genre : genreEntityList) {
            GenreDto genreDto = new GenreDto(
                    genre.getId(),
                    genre.getName(),
                    false
            );
            genreDtoList.add(genreDto);
        }

        for (Status status : statusEntityList) {
            StatusDto statusDto = new StatusDto(
                    status.getId(),
                    status.getName(),
                    false
            );
            statusDtoList.add(statusDto);
        }
    }

    private BookDto prepareBookDto(Long bookId) {

        List<Author> authors = authorRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        List<Status> statuses = statusRepository.findAll();

        Book book = bookRepository.getOne(bookId);
        Set<Author> authorsSelected = book.getAuthors();
        Set<Genre> genresSelected = book.getGenres();
        Status statusSelected = book.getStatus();

        List<Long> authorIds = new ArrayList<>();
        authorsSelected.forEach((e) -> authorIds.add(e.getId()));
        List<AuthorDto> authorDtoList = new ArrayList<AuthorDto>();
        for (Author author : authors) {
            AuthorDto authorDto = new AuthorDto(
                    author.getId(),
                    author.getFirstName(),
                    author.getSecondName(),
                    author.getThirdName(),
                    author.getBirthDate(),
                    authorIds.contains(author.getId())
            );
            authorDtoList.add(authorDto);
        }

        List<Long> genreIds = new ArrayList<Long>();
        genresSelected.forEach((e) -> genreIds.add(e.getId()));
        List<GenreDto> genreDtoList = new ArrayList<GenreDto>();
        for (Genre genre : genres) {
            GenreDto genreDto = new GenreDto(
                    genre.getId(),
                    genre.getName(),
                    genreIds.contains(genre.getId())
            );
            genreDtoList.add(genreDto);
        }

        List<Long> statusIds = new ArrayList<Long>();
        statusIds.add(statusSelected.getId());
        List<StatusDto> statusDtoList = new ArrayList<StatusDto>();
        for (Status status : statuses) {
            StatusDto statusDto = new StatusDto(
                    status.getId(),
                    status.getName(),
                    statusIds.contains(status.getId())
            );
            statusDtoList.add(statusDto);
        }

        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setName(book.getName());
        bookDto.setCreateDate(book.getCreateDate());
        bookDto.setDescription(book.getDescription());
        bookDto.setAuthors(authorDtoList);
        bookDto.setGenres(genreDtoList);
        bookDto.setStatuses(statusDtoList);

        String encodedImage = null;
        try {
            String base64SignatureImage = Base64.getEncoder().encodeToString(book.getImage());
            encodedImage = URLEncoder.encode(base64SignatureImage, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String extension = "data:image/jpg;base64,";
        bookDto.setImage(book.getImage());
        bookDto.setImageExtension(extension);
        bookDto.setImageString(extension + encodedImage);

        return bookDto;
    }

    private void checkStatusGenresAuthors(Status status, List<Genre> genres, List<Author> authors) throws Exception {
        if (status == null)
            throw new Exception("Status not found");

        if (genres == null || genres.isEmpty())
            throw new Exception("Genre not found");

        if (authors == null || authors.isEmpty())
            throw new Exception("Author not found");
    }
}

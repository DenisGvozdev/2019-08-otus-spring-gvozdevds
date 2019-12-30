package ru.gds.spring.service;

import org.apache.log4j.Logger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;
import ru.gds.spring.interfaces.AuthorRepository;
import ru.gds.spring.interfaces.BookRepository;
import ru.gds.spring.interfaces.GenreRepository;
import ru.gds.spring.interfaces.StatusRepository;
import ru.gds.spring.util.FileUtils;

import java.io.File;
import java.util.Date;
import java.util.List;

@ShellComponent
public class BookCommands {

    private static final Logger logger = Logger.getLogger(BookCommands.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;

    BookCommands(BookRepository br, GenreRepository gr, AuthorRepository ar, StatusRepository sr) {
        bookRepository = br;
        genreRepository = gr;
        authorRepository = ar;
        statusRepository = sr;
    }

    @ShellMethod(value = "add-book", key = "ab")
    public boolean addBook(String name, String description, String imagePath, long genreId, long statusId, long authorId) {
        Genre genre = genreRepository.getById(genreId);
        Status status = statusRepository.getById(statusId);
        Author author = authorRepository.getById(authorId);
        File image = FileUtils.getFile(imagePath);
        Book book = new Book(
                name,
                new Date(),
                description,
                FileUtils.convertFileToByteArray(image),
                genre,
                status,
                author);

        boolean result = bookRepository.insert(book);
        logger.debug(result ? "book successful added" : "Not found data");
        return result;
    }

    @ShellMethod(value = "get-all-books", key = "gab")
    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    @ShellMethod(value = "get-book-by-id", key = "gbbid")
    public Book getBookById(long id) {
        return bookRepository.getById(id);
    }

    @ShellMethod(value = "remove-book-by-id", key = "rbbid")
    public boolean removeBookById(long id) {
        boolean result = bookRepository.removeById(id);
        logger.debug(result ? "book successful deleted" : "book delete error");
        return result;
    }

    @ShellMethod(value = "update-book", key = "ub")
    public boolean updateBook(long id, String name, String description, String imagePath, long genreId, long statusId, long authorId) {
        Genre genre = genreRepository.getById(genreId);
        Status status = statusRepository.getById(statusId);
        Author author = authorRepository.getById(authorId);
        Book book = bookRepository.getById(id);
        book.setName(name);
        book.setDescription(description);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setStatus(status);
        File image = FileUtils.getFile(imagePath);
        book.setImage(FileUtils.convertFileToByteArray(image));

        boolean result = bookRepository.update(book);
        logger.debug(result ? "book successful updated" : "book update error");
        return result;
    }

    // --------- Работаем с жанрами --------------

    @ShellMethod(value = "add-genre", key = "ag")
    public boolean addGenre(String name) {
        Genre genre = new Genre(name);
        boolean result = genreRepository.insert(genre);
        logger.debug(result ? "genre success inserted" : "genre add error");
        return result;
    }

    @ShellMethod(value = "get-all-genre", key = "gag")
    public List<Genre> getAllGenres() {
        return genreRepository.getAll();
    }

    @ShellMethod(value = "get-genre-by-id", key = "ggbid")
    public Genre getGenreById(long id) {
        return genreRepository.getById(id);
    }

    @ShellMethod(value = "remove-genre-by-id", key = "rgbid")
    public boolean removeGenreById(long id) {
        boolean result = genreRepository.removeById(id);
        logger.debug(result ? "genre successful deleted" : "genre delete error");
        return result;
    }

    @ShellMethod(value = "update-genre", key = "ug")
    public boolean updateGenre(long id, String name) {
        Genre genre = genreRepository.getById(id);
        genre.setName(name);
        boolean result = genreRepository.update(genre);
        logger.debug(result ? "genre success updated" : "genre update error");
        return result;
    }

    // --------- Работаем с авторами --------------

    @ShellMethod(value = "add-author", key = "aa")
    public boolean addAuthor(String firstName, String secondName, String thirdName, Date birthDate, long statusId) {
        Status status = statusRepository.getById(statusId);
        Author author = new Author(firstName, secondName, thirdName, birthDate, status);
        boolean result = authorRepository.insert(author);
        logger.debug(result ? "author success inserted" : "author add error");
        return result;
    }

    @ShellMethod(value = "get-all-author", key = "gaa")
    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    @ShellMethod(value = "get-author-by-id", key = "gabid")
    public Author getAuthorById(long id) {
        return authorRepository.getById(id);
    }

    @ShellMethod(value = "remove-author-by-id", key = "rabid")
    public boolean removeAuthorById(long id) {
        boolean result = authorRepository.removeById(id);
        logger.debug(result ? "author successful deleted" : "author delete error");
        return result;
    }

    @ShellMethod(value = "update-author", key = "ua")
    public boolean updateAuthor(long id, String firstName, String secondName, String thirdName, Date birthDate, long statusId) {
        Status status = statusRepository.getById(statusId);
        Author author = authorRepository.getById(id);
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setThirdName(thirdName);
        author.setBirthDate(birthDate);
        author.setStatus(status);
        boolean result = authorRepository.update(author);
        logger.debug(result ? "author success updated" : "author update error");
        return result;
    }

    // --------- Работаем со статусами --------------

    @ShellMethod(value = "add-status", key = "as")
    public boolean addStatus(String name) {
        Status status = new Status(name);
        boolean result = statusRepository.insert(status);
        logger.debug(result ? "status success inserted" : "status add error");
        return result;
    }

    @ShellMethod(value = "get-all-status", key = "gas")
    public List<Status> getAllStatuses() {
        return statusRepository.getAll();
    }

    @ShellMethod(value = "get-status-by-id", key = "gsbid")
    public Status getStatusById(long id) {
        return statusRepository.getById(id);
    }

    @ShellMethod(value = "remove-status-by-id", key = "rsbid")
    public boolean removeStatusById(long id) {
        boolean result = statusRepository.removeById(id);
        logger.debug(result ? "status successful deleted" : "status delete error");
        return result;
    }

    @ShellMethod(value = "update-status", key = "us")
    public boolean updateStatus(long id, String name) {
        Status status = statusRepository.getById(id);
        status.setName(name);
        boolean result = statusRepository.update(status);
        logger.debug(result ? "status success updated" : "status update error");
        return result;
    }
}

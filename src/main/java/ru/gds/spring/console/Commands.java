package ru.gds.spring.console;

import org.apache.log4j.Logger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.gds.spring.constant.ConstantFormatDate;
import ru.gds.spring.domain.*;
import ru.gds.spring.interfaces.*;
import ru.gds.spring.util.DateUtils;
import ru.gds.spring.util.FileUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@ShellComponent
public class Commands {

    private static final Logger logger = Logger.getLogger(Commands.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;
    private final CommentRepository commentRepository;

    Commands(BookRepository br, GenreRepository gr, AuthorRepository ar, StatusRepository sr, CommentRepository cr) {
        bookRepository = br;
        genreRepository = gr;
        authorRepository = ar;
        statusRepository = sr;
        commentRepository = cr;
    }

    // --------- Работаем с книгами --------------
    // Пример: ab "Кольцо тьмы" "книга супер" "путь к файлу" 1 "1,2" "1,2"
    @ShellMethod(value = "add-book", key = "ab")
    public Book addBook(String name, String description, String imagePath,
                        long statusId, String genreIds, String authorIds) {

        Status status = statusRepository.findById(statusId);
        List<Genre> genres = genreRepository.loadGenresByIdString(genreIds);
        List<Author> authors = authorRepository.loadAuthorsByIdString(authorIds);
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
        return book;
    }

    @ShellMethod(value = "get-all-books", key = "gab")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @ShellMethod(value = "get-book-by-id", key = "gbbid")
    public Book getBookById(long id) {
        return bookRepository.findById(id);
    }

    @ShellMethod(value = "remove-book-by-id", key = "rbbid")
    public boolean removeBookById(long id) {
        boolean result = bookRepository.deleteById(id);
        logger.debug(result ? "book successful deleted" : "book delete error");
        return result;
    }

    @ShellMethod(value = "update-book", key = "ub")
    public boolean updateBook(long id, String name, String description, String imagePath,
                              long statusId, String genreIds, String authorIds) {

        List<Genre> genres = genreRepository.loadGenresByIdString(genreIds);
        List<Author> authors = authorRepository.loadAuthorsByIdString(authorIds);
        Status status = statusRepository.findById(statusId);
        File image = FileUtils.getFile(imagePath);

        Book book = bookRepository.findById(id);
        book.setName(name);
        book.setDescription(description);
        book.setGenres(new HashSet<>(genres));
        book.setAuthors(new HashSet<>(authors));
        book.setStatus(status);
        book.setImage(FileUtils.convertFileToByteArray(image));

        boolean result = bookRepository.updateById(book);
        logger.debug(result ? "book successful updated" : "book update error");
        return result;
    }

    // --------- Работаем с жанрами --------------
    // Пример: ag Фантастика
    @ShellMethod(value = "add-genre", key = "ag")
    public Genre addGenre(String name) {
        Genre genre = new Genre(name);
        genre = genreRepository.save(genre);
        long id = genre.getId();
        logger.debug((id > 0) ? "status success inserted" : "status add error");
        return genre;
    }

    @ShellMethod(value = "get-all-genre", key = "gag")
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @ShellMethod(value = "get-genre-by-id", key = "ggbid")
    public Genre getGenreById(long id) {
        return genreRepository.findById(id);
    }

    @ShellMethod(value = "remove-genre-by-id", key = "rgbid")
    public boolean removeGenreById(long id) {
        boolean result = genreRepository.deleteById(id);
        logger.debug(result ? "genre successful deleted" : "genre delete error");
        return result;
    }

    @ShellMethod(value = "update-genre", key = "ug")
    public boolean updateGenre(long id, String name) {
        Genre genre = genreRepository.findById(id);
        if (genre == null) {
            logger.debug("genre not found by id");
            return false;
        }

        genre.setName(name);
        boolean result = genreRepository.updateById(genre);
        logger.debug(result ? "genre successful updated" : "genre update error");

        return result;
    }

    // --------- Работаем с авторами --------------
    // Пример: aa Иванов Иван Иванович 1980-01-01
    @ShellMethod(value = "add-author", key = "aa")
    public Author addAuthor(String firstName, String secondName, String thirdName, String birthDate) {
        try {
            Date date = DateUtils.getDateFromString(birthDate, ConstantFormatDate.FORMAT_1);
            Author author = new Author(firstName, secondName, thirdName, date);
            author = authorRepository.save(author);
            long id = author.getId();
            logger.debug((id > 0) ? "author success inserted" : "author add error");
            return author;
        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
        return null;
    }

    @ShellMethod(value = "get-all-author", key = "gaa")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @ShellMethod(value = "get-author-by-id", key = "gabid")
    public Author getAuthorById(long id) {
        return authorRepository.findById(id);
    }

    @ShellMethod(value = "remove-author-by-id", key = "rabid")
    public boolean removeAuthorById(long id) {
        boolean result = authorRepository.deleteById(id);
        logger.debug(result ? "author successful deleted" : "author delete error");
        return result;
    }

    @ShellMethod(value = "update-author", key = "ua")
    public boolean updateAuthor(long id, String firstName, String secondName, String thirdName, Date birthDate) {
        Author author = authorRepository.findById(id);
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setThirdName(thirdName);
        author.setBirthDate(birthDate);
        boolean result = authorRepository.updateById(author);
        logger.debug(result ? "author success updated" : "author update error");
        return result;
    }

    // --------- Работаем со статусами --------------
    // Пример: as active
    @ShellMethod(value = "add-status", key = "as")
    public Status addStatus(String name) {
        Status status = new Status(name);
        status = statusRepository.save(status);
        long id = status.getId();
        logger.debug((id > 0) ? "status success inserted" : "status add error");
        return status;
    }

    @ShellMethod(value = "get-all-status", key = "gas")
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    @ShellMethod(value = "get-status-by-id", key = "gsbid")
    public Status getStatusById(long id) {
        return statusRepository.findById(id);
    }

    @ShellMethod(value = "remove-status-by-id", key = "rsbid")
    public boolean removeStatusById(long id) {
        boolean result = statusRepository.deleteById(id);
        logger.debug(result ? "status successful deleted" : "status delete error");
        return result;
    }

    @ShellMethod(value = "update-status", key = "us")
    public boolean updateStatus(long id, String name) {
        Status status = statusRepository.findById(id);
        if (status == null) {
            logger.debug("status not found by id = " + id);
            return false;
        }

        status.setName(name);
        boolean result = statusRepository.updateById(status);
        logger.debug(result ? "status successful updated" : "status update error");
        return result;
    }

    // --------- Работаем с комментариями --------------
    // Пример: ac 1 "Тестовый комментарий к книге"
    @ShellMethod(value = "add-comment", key = "ac")
    public Comment addComment(long bookId, String text) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            logger.debug("comment update error because Book not found");
            return null;
        }
        Comment comment = new Comment(book, text, new Date());
        comment = commentRepository.save(comment);
        long id = comment.getId();
        logger.debug((id > 0) ? "comment success inserted" : "comment add error");
        return comment;
    }

    @ShellMethod(value = "get-all-comments", key = "gac")
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    @ShellMethod(value = "get-comments-by-book-id", key = "gcbbid")
    public List<Comment> getCommentByBookId(long bookId) {
        return commentRepository.findByBookId(bookId);
    }

    @ShellMethod(value = "remove-comment-by-id", key = "rcbid")
    public boolean removeCommentById(long id) {
        boolean result = commentRepository.deleteById(id);
        logger.debug(result ? "comment successful deleted" : "comment delete error");
        return result;
    }

    @ShellMethod(value = "update-comment", key = "uc")
    public boolean updateComment(long id, String text) {
        Comment comment = commentRepository.findById(id);
        if (comment == null) {
            logger.debug("comment not found by id = " + id);
            return false;
        }
        comment.setComment(text);
        comment.setCreateDate(new Date());
        boolean result = commentRepository.updateById(comment);
        logger.debug(result ? "comment successful updated" : "comment update error");
        return result;
    }
}

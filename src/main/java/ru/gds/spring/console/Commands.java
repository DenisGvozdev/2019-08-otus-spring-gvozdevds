package ru.gds.spring.console;

import org.apache.log4j.Logger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.gds.spring.constant.ConstantFormatDate;
import ru.gds.spring.domain.*;
import ru.gds.spring.interfaces.*;
import ru.gds.spring.util.CommonUtils;
import ru.gds.spring.util.DateUtils;
import ru.gds.spring.util.FileUtils;
import ru.gds.spring.util.PrintUtils;

import java.io.File;
import java.util.*;

@ShellComponent
public class Commands {

    private static final Logger logger = Logger.getLogger(Commands.class);

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final StatusRepository statusRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    Commands(
            BookRepository br,
            GenreRepository gr,
            AuthorRepository ar,
            StatusRepository sr,
            CommentRepository cr,
            UserRepository ur) {
        bookRepository = br;
        genreRepository = gr;
        authorRepository = ar;
        statusRepository = sr;
        commentRepository = cr;
        userRepository = ur;
    }

    // --------- Работаем с книгами --------------
    // Пример: ab "Кольцо тьмы" "книга супер" "путь к файлу" "active" "Фэнтези,Приключения" "Перумов,Дефо"
    @ShellMethod(value = "add-book", key = "ab")
    public Book addBook(String name, String description, String imagePath,
                        String statusName, String genreNames, String authorThirdNames) {

        List<Status> statuses = statusRepository.findAllByName(statusName, null);
        List<Genre> genres = genreRepository.findAllByName(CommonUtils.convertStringToListString(genreNames), null);
        List<Author> authors = authorRepository.findAllByName(CommonUtils.convertStringToListString(authorThirdNames), null);
        File image = FileUtils.getFile(imagePath);

        Book book = new Book(
                name,
                new Date(),
                description,
                FileUtils.convertFileToByteArray(image),
                genres,
                authors,
                statuses.get(0));
        book = bookRepository.save(book);
        logger.debug("create book successful");
        return book;
    }

    @ShellMethod(value = "get-all-books", key = "gab")
    public List<Book> getAllBooks() {
        List<Book> list = bookRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "get-book-by-id", key = "gbbid")
    public Book getBookById(String id) {
        Book book = bookRepository.findById(id).get();
        logger.debug(PrintUtils.printObject(null, book));
        return book;
    }

    @ShellMethod(value = "remove-book-by-id", key = "rbbid")
    public boolean removeBookById(String id) {
        bookRepository.deleteById(id);
        logger.debug("book successful deleted");
        return true;
    }

    @ShellMethod(value = "update-book", key = "ub")
    public boolean updateBook(String id, String name, String description, String imagePath,
                              String statusId, String genreIds, String authorIds) {

        List<Genre> genres = genreRepository.findAllById(CommonUtils.convertStringToListString(genreIds));
        List<Author> authors = authorRepository.findAllById(CommonUtils.convertStringToListString(authorIds), null);
        Status status = statusRepository.findById(statusId).get();
        File image = FileUtils.getFile(imagePath);

        Book book = bookRepository.findById(id).get();
        book.setName(name);
        book.setDescription(description);
        book.setGenres(genres);
        book.setAuthors(authors);
        book.setStatus(status);
        book.setImage(FileUtils.convertFileToByteArray(image));

        book = bookRepository.save(book);
        logger.debug("book successful updated");
        return name.equals(book.getName());
    }

    // --------- Работаем с жанрами --------------
    // Пример: ag Фантастика
    @ShellMethod(value = "add-genre", key = "ag")
    public Genre addGenre(String name) {
        Genre genre = new Genre(name);
        genre = genreRepository.save(genre);
        logger.debug("status success inserted");
        return genre;
    }

    @ShellMethod(value = "get-all-genre", key = "gag")
    public List<Genre> getAllGenres() {
        List<Genre> list = genreRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "get-genre-by-id", key = "ggbid")
    public Genre getGenreById(String id) {
        Genre genre = genreRepository.findById(id).get();
        logger.debug(PrintUtils.printObject(null, genre));
        return genre;
    }

    @ShellMethod(value = "remove-genre-by-id", key = "rgbid")
    public boolean removeGenreById(String id) {
        genreRepository.deleteById(id);
        logger.debug("genre successful deleted");
        return true;
    }

    @ShellMethod(value = "update-genre", key = "ug")
    public boolean updateGenre(String id, String name) {
        Genre genre = genreRepository.findById(id).get();
        genre.setName(name);
        genre = genreRepository.save(genre);
        logger.debug("genre successful updated");

        return name.equals(genre.getName());
    }

    // --------- Работаем с авторами --------------
    // Пример: aa Иванов Иван Иванович 1980-01-01
    @ShellMethod(value = "add-author", key = "aa")
    public Author addAuthor(String firstName, String secondName, String thirdName, String birthDate) {
        try {
            Date date = DateUtils.getDateFromString(birthDate, ConstantFormatDate.FORMAT_1);
            Author author = new Author(firstName, secondName, thirdName, date);
            author = authorRepository.save(author);
            logger.debug("author success inserted");
            return author;
        } catch (Exception e) {
            logger.error(Arrays.asList(e.getStackTrace()));
        }
        return null;
    }

    @ShellMethod(value = "get-all-author", key = "gaa")
    public List<Author> getAllAuthors() {
        List<Author> list = authorRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return authorRepository.findAll();
    }

    @ShellMethod(value = "get-author-by-id", key = "gabid")
    public Author getAuthorById(String id) {
        Author author = authorRepository.findById(id).get();
        logger.debug(PrintUtils.printObject(null, author));
        return author;
    }

    @ShellMethod(value = "remove-author-by-id", key = "rabid")
    public boolean removeAuthorById(String id) {
        authorRepository.deleteById(id);
        logger.debug("author successful deleted");
        return true;
    }

    @ShellMethod(value = "update-author", key = "ua")
    public boolean updateAuthor(String id, String firstName, String secondName, String thirdName, Date birthDate) {
        Author author = authorRepository.findById(id).get();
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setThirdName(thirdName);
        author.setBirthDate(birthDate);
        author = authorRepository.save(author);
        logger.debug("author success updated: " + PrintUtils.printObject(null, author));
        return firstName.equals(author.getFirstName());
    }

    // --------- Работаем со статусами --------------
    // Пример: as active
    @ShellMethod(value = "add-status", key = "as")
    public Status addStatus(String name) {
        Status status = new Status(name);
        status = statusRepository.save(status);
        logger.debug("status success inserted");
        return status;
    }

    @ShellMethod(value = "get-all-status", key = "gas")
    public List<Status> getAllStatuses() {
        List<Status> list = statusRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "get-status-by-id", key = "gsbid")
    public Status getStatusById(String id) {
        Status status = statusRepository.findById(id).get();
        logger.debug(PrintUtils.printObject(null, status));
        return status;
    }

    @ShellMethod(value = "remove-status-by-id", key = "rsbid")
    public boolean removeStatusById(String id) {
        statusRepository.deleteById(id);
        logger.debug("status successful deleted");
        return true;
    }

    @ShellMethod(value = "update-status", key = "us")
    public boolean updateStatus(String id, String name) {
        Status status = statusRepository.findById(id).get();
        status.setName(name);
        status = statusRepository.save(status);
        logger.debug("status successful updated");
        return name.equals(status.getName());
    }

    // --------- Работаем с комментариями --------------
    // Пример: ac 1 "Тестовый комментарий к книге"
    @ShellMethod(value = "add-comment", key = "ac")
    public Comment addComment(String bookId, String text) {
        Book book = bookRepository.findById(bookId).get();
        Comment comment = new Comment(book, text, new Date());
        comment = commentRepository.save(comment);
        logger.debug("comment success inserted");
        return comment;
    }

    @ShellMethod(value = "get-all-comments", key = "gac")
    public List<Comment> getAllComments() {
        List<Comment> list = commentRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "get-comments-by-book-id", key = "gcbbid")
    public List<Comment> getCommentByBookId(String bookId) {
        List<Comment> list = commentRepository.findByBookId(bookId, null);
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "remove-comment-by-id", key = "rcbid")
    public boolean removeCommentById(String id) {
        commentRepository.deleteById(id);
        logger.debug("comment successful deleted");
        return true;
    }

    @ShellMethod(value = "update-comment", key = "uc")
    public boolean updateComment(String id, String text) {
        Comment comment = commentRepository.findById(id).get();
        comment.setComment(text);
        comment.setCreateDate(new Date());
        comment = commentRepository.save(comment);
        logger.debug("comment successful updated");
        return text.equals(comment.getComment());
    }

    // --------- Работаем с пользователями --------------
    // Пример: au ivanovii password ivan@mail.ru +79672522523 Иванов Иван Иванович
    @ShellMethod(value = "add-user", key = "au")
    public User addUser(String login,
                        String password,
                        String email,
                        String phone,
                        String firstName,
                        String secondName,
                        String thirdName) {
        User user = new User(login, password, email, phone, firstName, secondName, thirdName);
        user = userRepository.save(user);
        logger.debug("user success inserted");
        return user;
    }

    @ShellMethod(value = "get-all-users", key = "gau")
    public List<User> getAllUser() {
        List<User> list = userRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "get-user-by-login", key = "gubl")
    public User getUserByLogin(String login) {
        User user = userRepository.findByLogin(login);
        logger.debug(PrintUtils.printObject(null, user));
        return user;
    }

    @ShellMethod(value = "remove-user-by-login", key = "rubl")
    public boolean removeUserByLogin(String id) {
        userRepository.deleteByLogin(id);
        logger.debug("user successful deleted");
        return true;
    }

    @ShellMethod(value = "update-user", key = "uu")
    public boolean updateUser(
            String login,
            String password,
            String email,
            String phone,
            String firstName,
            String secondName,
            String thirdName) {

        User user = userRepository.findByLogin(login);
        if (user == null) {
            logger.debug("user not found by login = " + login);
            return false;
        }
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setThirdName(thirdName);
        user = userRepository.save(user);
        logger.debug("user successful updated");
        return firstName.equals(user.getFirstName());
    }
}

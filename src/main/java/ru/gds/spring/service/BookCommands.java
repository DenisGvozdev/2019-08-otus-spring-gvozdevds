package ru.gds.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Date;
import java.util.List;

@ShellComponent
public class BookCommands {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private StatusRepository statusRepository;

    @ShellMethod("add-book")
    public boolean addBook(String name, String description, byte[] image, long genreId, long statusId, long authorId) {
        Genre genre = genreRepository.getById(genreId);
        Status status = statusRepository.getById(statusId);
        Author author = authorRepository.getById(authorId);
        Book book = new Book(name, new Date(), description, image, genre, status, author);

        boolean result = bookRepository.insert(book);
        System.out.println(result ? "book successful added" : "Not found data");
        return result;
    }

    @ShellMethod("get-all-books")
    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    @ShellMethod("get-book-by-id")
    public Book getBookById(long id) {
        return bookRepository.getById(id);
    }

    @ShellMethod("remove-book-by-id")
    public boolean removeBookById(long id) {
        boolean result = bookRepository.removeById(id);
        System.out.println(result ? "book successful deleted" : "book delete error");
        return result;
    }

    @ShellMethod("update-book")
    public boolean updateBook(long id, String name, String description, byte[] image, long genreId, long statusId, long authorId) {
        Genre genre = genreRepository.getById(genreId);
        Status status = statusRepository.getById(statusId);
        Author author = authorRepository.getById(authorId);
        Book book = bookRepository.getById(id);
        book.setName(name);
        book.setDescription(description);
        book.setImage(image);
        book.setGenre(genre);
        book.setAuthor(author);
        book.setStatus(status);

        boolean result = bookRepository.update(book);
        System.out.println(result ? "book successful updated" : "book update error");
        return result;
    }

    // --------- Работаем с жанрами --------------

    @ShellMethod("add-genre")
    public boolean addGenre(String name) {
        Genre genre = new Genre(name);
        boolean result = genreRepository.insert(genre);
        System.out.println(result ? "genre success inserted" : "genre add error");
        return result;
    }

    @ShellMethod("get-all-genre")
    public List<Genre> getAllGenres() {
        return genreRepository.getAll();
    }

    @ShellMethod("get-genre-by-id")
    public Genre getGenreById(long id) {
        return genreRepository.getById(id);
    }

    @ShellMethod("remove-genre-by-id")
    public boolean removeGenreById(long id) {
        boolean result = genreRepository.removeById(id);
        System.out.println(result ? "genre successful deleted" : "genre delete error");
        return result;
    }

    @ShellMethod("update-genre")
    public boolean updateGenre(long id, String name) {
        Genre genre = genreRepository.getById(id);
        genre.setName(name);
        boolean result = genreRepository.update(genre);
        System.out.println(result ? "genre success updated" : "genre update error");
        return result;
    }

    // --------- Работаем с авторами --------------

    @ShellMethod("add-author")
    public boolean addAuthor(String firstName, String secondName, String thirdName, Date birthDate, long statusId) {
        Status status = statusRepository.getById(statusId);
        Author author = new Author(firstName, secondName, thirdName, birthDate, status);
        boolean result = authorRepository.insert(author);
        System.out.println(result ? "author success inserted" : "author add error");
        return result;
    }

    @ShellMethod("get-all-author")
    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    @ShellMethod("get-author-by-id")
    public Author getAuthorById(long id) {
        return authorRepository.getById(id);
    }

    @ShellMethod("remove-author-by-id")
    public boolean removeAuthorById(long id) {
        boolean result = authorRepository.removeById(id);
        System.out.println(result ? "author successful deleted" : "author delete error");
        return result;
    }

    @ShellMethod("update-author")
    public boolean updateAuthor(long id, String firstName, String secondName, String thirdName, Date birthDate, long statusId) {
        Status status = statusRepository.getById(statusId);
        Author author = authorRepository.getById(id);
        author.setFirstName(firstName);
        author.setSecondName(secondName);
        author.setThirdName(thirdName);
        author.setBirthDate(birthDate);
        author.setStatus(status);
        boolean result = authorRepository.update(author);
        System.out.println(result ? "author success updated" : "author update error");
        return result;
    }

    // --------- Работаем со статусами --------------

    @ShellMethod("add-status")
    public boolean addStatus(String name) {
        Status status = new Status(name);
        boolean result = statusRepository.insert(status);
        System.out.println(result ? "status success inserted" : "status add error");
        return result;
    }

    @ShellMethod("get-all-status")
    public List<Status> getAllStatuses() {
        return statusRepository.getAll();
    }

    @ShellMethod("get-status-by-id")
    public Status getStatusById(long id) {
        return statusRepository.getById(id);
    }

    @ShellMethod("remove-status-by-id")
    public boolean removeStatusById(long id) {
        boolean result = statusRepository.removeById(id);
        System.out.println(result ? "status successful deleted" : "status delete error");
        return result;
    }

    @ShellMethod("update-status")
    public boolean updateStatus(long id, String name) {
        Status status = statusRepository.getById(id);
        status.setName(name);
        boolean result = statusRepository.update(status);
        System.out.println(result ? "status success updated" : "status update error");
        return result;
    }
}

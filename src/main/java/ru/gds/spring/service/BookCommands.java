package ru.gds.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.gds.spring.dao.JdbcAuthorRepository;
import ru.gds.spring.dao.JdbcBookRepository;
import ru.gds.spring.dao.JdbcGenreRepository;
import ru.gds.spring.dao.JdbcStatusRepository;
import ru.gds.spring.domain.Author;
import ru.gds.spring.domain.Book;
import ru.gds.spring.domain.Genre;
import ru.gds.spring.domain.Status;

import java.util.Date;
import java.util.List;

@ShellComponent
public class BookCommands {

    @Autowired
    @Qualifier("jdbcBookRepository")
    private JdbcBookRepository bookRepository;

    @Autowired
    @Qualifier("jdbcGenreRepository")
    private JdbcGenreRepository genreRepository;

    @Autowired
    @Qualifier("jdbcAuthorRepository")
    private JdbcAuthorRepository authorRepository;

    @Autowired
    @Qualifier("jdbcStatusRepository")
    private JdbcStatusRepository statusRepository;

    @ShellMethod("add-book")
    public boolean addBook(String name, long genreId, long statusId, long authorId) {
        boolean result = bookRepository.insert(name, new Date(), "", null, genreId, statusId, authorId);
        if (Boolean.TRUE.equals(result))
            System.out.println("book successful added");
        else
            System.out.println("Not found data");

        return result;
    }

    @ShellMethod("get-all-books")
    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    @ShellMethod("get-book-by-id")
    public Book getBookById(String id) {
        return bookRepository.getById(Long.valueOf(id));
    }

    @ShellMethod("remove-book-by-id")
    public boolean removeBookById(String id) {
        boolean result = bookRepository.removeById(Long.valueOf(id));
        if (Boolean.TRUE.equals(result))
            System.out.println("book successful deleted");
        else
            System.out.println("book delete error");

        return result;
    }

    @ShellMethod("update-book")
    public boolean updateBook(long id, String name) {
        boolean result = bookRepository.update(id, name, new Date(), "Описание", null, 2, 1, 2);
        if (Boolean.TRUE.equals(result))
            System.out.println("book success updated");
        else
            System.out.println("book update error");

        return result;
    }

    // --------- Работаем с жанрами --------------

    @ShellMethod("add-genre")
    public boolean addGenre(String name) {
        boolean result = genreRepository.insert(name);
        if (Boolean.TRUE.equals(result))
            System.out.println("genre success inserted");
        else
            System.out.println("genre add error");

        return result;
    }

    @ShellMethod("get-all-genre")
    public List<Genre> getAllGenres() {
        return genreRepository.getAll();
    }

    @ShellMethod("get-genre-by-id")
    public Genre getGenreById(String id) {
        return genreRepository.getById(Long.valueOf(id));
    }

    @ShellMethod("remove-genre-by-id")
    public boolean removeGenreById(String id) {
        boolean result = genreRepository.removeById(Long.valueOf(id));
        if (Boolean.TRUE.equals(result))
            System.out.println("genre successful deleted");
        else
            System.out.println("genre delete error");

        return result;
    }

    @ShellMethod("update-genre")
    public boolean updateGenre(long id, String name) {
        boolean result = genreRepository.update(id, name);
        if (Boolean.TRUE.equals(result))
            System.out.println("genre success updated");
        else
            System.out.println("genre update error");

        return result;
    }

    // --------- Работаем с авторами --------------

    @ShellMethod("add-author")
    public boolean addAuthor(String firstName, String secondName, String thirdName, Date birthDate, long status) {
        boolean result = authorRepository.insert(firstName, secondName, thirdName, birthDate, status);
        if (Boolean.TRUE.equals(result))
            System.out.println("author success inserted");
        else
            System.out.println("author add error");

        return result;
    }

    @ShellMethod("get-all-author")
    public List<Author> getAllAuthors() {
        return authorRepository.getAll();
    }

    @ShellMethod("get-author-by-id")
    public Author getAuthorById(String id) {
        return authorRepository.getById(Long.valueOf(id));
    }

    @ShellMethod("remove-author-by-id")
    public boolean removeAuthorById(String id) {
        boolean result = authorRepository.removeById(Long.valueOf(id));
        if (Boolean.TRUE.equals(result))
            System.out.println("author successful deleted");
        else
            System.out.println("author delete error");

        return result;
    }

    @ShellMethod("update-author")
    public boolean updateAuthor(long id, String firstName, String secondName, String thirdName, Date birthDate, long status) {
        boolean result = authorRepository.update(id, firstName, secondName, thirdName, birthDate, status);
        if (Boolean.TRUE.equals(result))
            System.out.println("author success updated");
        else
            System.out.println("author update error");

        return result;
    }

    // --------- Работаем со статусами --------------

    @ShellMethod("add-status")
    public boolean addStatus(String name) {
        boolean result = statusRepository.insert(name);
        if (Boolean.TRUE.equals(result))
            System.out.println("status success inserted");
        else
            System.out.println("status add error");

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
        if (Boolean.TRUE.equals(result))
            System.out.println("status successful deleted");
        else
            System.out.println("status delete error");

        return result;
    }

    @ShellMethod("update-status")
    public boolean updateStatus(long id, String name) {
        boolean result = statusRepository.update(id, name);
        if (Boolean.TRUE.equals(result))
            System.out.println("status success updated");
        else
            System.out.println("status update error");

        return result;
    }
}

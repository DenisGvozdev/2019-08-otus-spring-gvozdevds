package ru.gds.spring.console;

import org.apache.log4j.Logger;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.gds.spring.batch.BatchService;
import ru.gds.spring.jpa.domain.*;
import ru.gds.spring.jpa.interfaces.*;
import ru.gds.spring.util.PrintUtils;

import java.util.*;

@ShellComponent
public class Commands {

    private static final Logger logger = Logger.getLogger(Commands.class);

    private final BatchService batchService;

    private final AuthorJpaRepository authorJpaRepository;
    private final GenreJpaRepository genreJpaRepository;
    private final StatusJpaRepository statusJpaRepository;
    private final BookJpaRepository bookJpaRepository;
    private final CommentJpaRepository commentJpaRepository;

    Commands(BatchService batchService,
             AuthorJpaRepository authorJpaRepository,
             GenreJpaRepository genreJpaRepository,
             StatusJpaRepository statusJpaRepository,
             BookJpaRepository bookJpaRepository,
             CommentJpaRepository commentJpaRepository) {

        this.batchService = batchService;
        this.authorJpaRepository = authorJpaRepository;
        this.genreJpaRepository = genreJpaRepository;
        this.statusJpaRepository = statusJpaRepository;
        this.bookJpaRepository = bookJpaRepository;
        this.commentJpaRepository = commentJpaRepository;
    }

    @ShellMethod(value = "migrate-all-database", key = "madb")
    public void migrateAll() {
        batchService.launchImportDataBseJob();
    }

    @ShellMethod(value = "get-all-authors", key = "gaa")
    public List<Author> getAllAuthors() {
        List<Author> list = authorJpaRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "get-all-genres", key = "gag")
    public List<Genre> getAllGenres() {
        List<Genre> list = genreJpaRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "get-all-statuses", key = "gas")
    public List<Status> getAllStatuses() {
        List<Status> list = statusJpaRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "get-all-books", key = "gab")
    public List<Book> getAllBooks() {
        List<Book> list = bookJpaRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }

    @ShellMethod(value = "get-all-comments", key = "gac")
    public List<Comment> getAllComments() {
        List<Comment> list = commentJpaRepository.findAll();
        logger.debug(PrintUtils.printObject(null, list));
        return list;
    }
}

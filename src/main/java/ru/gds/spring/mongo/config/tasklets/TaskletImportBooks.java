package ru.gds.spring.mongo.config.tasklets;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import ru.gds.spring.jpa.domain.Author;
import ru.gds.spring.jpa.domain.Book;
import ru.gds.spring.jpa.domain.Genre;
import ru.gds.spring.jpa.domain.Status;
import ru.gds.spring.jpa.interfaces.AuthorJpaRepository;
import ru.gds.spring.jpa.interfaces.BookJpaRepository;
import ru.gds.spring.jpa.interfaces.GenreJpaRepository;
import ru.gds.spring.jpa.interfaces.StatusJpaRepository;
import ru.gds.spring.mongo.domain.BookMongo;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TaskletImportBooks implements Tasklet, StepExecutionListener {

    private static final Logger logger = Logger.getLogger(TaskletImportBooks.class);

    private final MongoTemplate mongoTemplate;
    private final BookJpaRepository bookJpaRepository;
    private final GenreJpaRepository genreJpaRepository;
    private final AuthorJpaRepository authorJpaRepository;
    private final StatusJpaRepository statusJpaRepository;

    private List<BookMongo> bookMongoList;
    private List<Book> bookList;

    private static final int BATCH = 5;

    public TaskletImportBooks(
            MongoTemplate mongoTemplate,
            BookJpaRepository bookJpaRepository,
            GenreJpaRepository genreJpaRepository,
            AuthorJpaRepository authorJpaRepository,
            StatusJpaRepository statusJpaRepository) {

        this.mongoTemplate = mongoTemplate;
        this.bookJpaRepository = bookJpaRepository;
        this.genreJpaRepository = genreJpaRepository;
        this.authorJpaRepository = authorJpaRepository;
        this.statusJpaRepository = statusJpaRepository;

        bookMongoList = new ArrayList<>();
        bookList = new ArrayList<>();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("TaskletImportBooks initialized");
        bookList.clear();
        bookMongoList = mongoTemplate.findAll(BookMongo.class);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {

            for (BookMongo bookMongo : bookMongoList) {

                List<Status> statusList = statusJpaRepository.findAllByName(bookMongo.getStatus().getName(), null);

                List<String> genreNameList = bookMongo.getGenres()
                        .stream()
                        .map(genreMongo -> genreMongo.getName())
                        .collect(Collectors.toList());
                List<Genre> genreList = genreJpaRepository.findAllByName(genreNameList, null);

                List<String> authorFioList = bookMongo.getAuthors()
                        .stream()
                        .map(authorMongo -> (authorMongo.getFirstName() + authorMongo.getSecondName() + authorMongo.getThirdName()))
                        .collect(Collectors.toList());

                List<Author> authorList = new ArrayList<Author>();
                for (String fio : authorFioList)
                    authorList.addAll(authorJpaRepository.findAllByFirstNameAndSecondNameAndThirdName(fio));

                if (genreList.isEmpty() || authorList.isEmpty() || statusList.isEmpty()) {
                    logger.error("error migrate book " + bookMongo.getName()
                            + " because authors or genres or status is empty");
                    continue;
                }

                bookList.add(
                        new Book(
                                bookMongo.getName(),
                                bookMongo.getCreateDate(),
                                bookMongo.getDescription(),
                                bookMongo.getImage(),
                                new HashSet<Genre>(genreList),
                                new HashSet<Author>(authorList),
                                statusList.get(0)));
            }

            int counter = 0;
            List<Book> bookBatchList = new ArrayList<Book>();
            for (Book book : bookList) {
                bookBatchList.add(book);
                counter += 1;

                if (counter == BATCH || counter == bookList.size()) {
                    bookJpaRepository.saveAll(bookBatchList);
                    bookBatchList.clear();
                    counter = 0;
                }
            }

        } catch (Exception e) {
            logger.error("TaskletImportBooks execute error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("TaskletImportBooks finished");
        return ExitStatus.COMPLETED;
    }
}

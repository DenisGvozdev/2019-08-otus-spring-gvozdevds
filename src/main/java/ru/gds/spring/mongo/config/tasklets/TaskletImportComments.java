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
import ru.gds.spring.jpa.domain.Book;
import ru.gds.spring.jpa.domain.Comment;
import ru.gds.spring.jpa.interfaces.BookJpaRepository;
import ru.gds.spring.jpa.interfaces.CommentJpaRepository;
import ru.gds.spring.mongo.domain.CommentMongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TaskletImportComments implements Tasklet, StepExecutionListener {

    private static final Logger logger = Logger.getLogger(TaskletImportComments.class);

    private final MongoTemplate mongoTemplate;
    private final CommentJpaRepository commentJpaRepository;
    private final BookJpaRepository bookJpaRepository;

    private List<CommentMongo> commentMongoList;
    private List<Comment> commentList;

    private static final int BATCH = 5;

    public TaskletImportComments(
            MongoTemplate mongoTemplate,
            CommentJpaRepository commentJpaRepository,
            BookJpaRepository bookJpaRepository) {

        this.mongoTemplate = mongoTemplate;
        this.commentJpaRepository = commentJpaRepository;
        this.bookJpaRepository = bookJpaRepository;
        commentMongoList = new ArrayList<>();
        commentList = new ArrayList<>();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("TaskletImportComments initialized");
        commentList.clear();
        commentMongoList = mongoTemplate.findAll(CommentMongo.class);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {
            for (CommentMongo commentMongo : commentMongoList) {

                List<Book> bookList = bookJpaRepository.findAllByName(commentMongo.getBook().getName());
                if (bookList.isEmpty()) {
                    logger.error("error migrate comment for book "
                            + commentMongo.getBook().getName()
                            + " because book not found");
                    continue;
                }

                commentList.add(new Comment(bookList.get(0), commentMongo.getComment(), commentMongo.getCreateDate()));
            }

            int counter = 0;
            List<Comment> commentBatchList = new ArrayList<Comment>();
            for (Comment comment : commentList) {
                commentBatchList.add(comment);
                counter += 1;

                if (counter == BATCH || counter == commentList.size()) {
                    commentJpaRepository.saveAll(commentBatchList);
                    commentBatchList.clear();
                    counter = 0;
                }
            }

        } catch (Exception e) {
            logger.error("TaskletImportComments execute error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("TaskletImportComments finished");
        return ExitStatus.COMPLETED;
    }
}

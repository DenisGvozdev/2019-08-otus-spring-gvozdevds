package ru.gds.spring.mongo.config.tasklets;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import ru.gds.spring.jpa.interfaces.*;

@Component
public class TaskletTablesClear implements Tasklet, StepExecutionListener {

    private static final Logger logger = Logger.getLogger(TaskletTablesClear.class);

    private final AuthorJpaRepository authorJpaRepository;
    private final GenreJpaRepository genreJpaRepository;
    private final StatusJpaRepository statusJpaRepository;
    private final BookJpaRepository bookJpaRepository;
    private final CommentJpaRepository commentJpaRepository;

    public TaskletTablesClear(
            AuthorJpaRepository authorJpaRepository,
            GenreJpaRepository genreJpaRepository,
            StatusJpaRepository statusJpaRepository,
            BookJpaRepository bookJpaRepository,
            CommentJpaRepository commentJpaRepository
    ){
        this.authorJpaRepository = authorJpaRepository;
        this.genreJpaRepository = genreJpaRepository;
        this.statusJpaRepository = statusJpaRepository;
        this.bookJpaRepository = bookJpaRepository;
        this.commentJpaRepository = commentJpaRepository;
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("TablesClear initialized");
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        commentJpaRepository.deleteAll();
        bookJpaRepository.deleteAll();
        authorJpaRepository.deleteAll();
        genreJpaRepository.deleteAll();
        statusJpaRepository.deleteAll();
        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("TablesClear finished");
        return ExitStatus.COMPLETED;
    }
}

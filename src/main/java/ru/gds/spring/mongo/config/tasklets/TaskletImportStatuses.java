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
import ru.gds.spring.jpa.domain.Status;
import ru.gds.spring.jpa.interfaces.StatusJpaRepository;
import ru.gds.spring.mongo.domain.StatusMongo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TaskletImportStatuses implements Tasklet, StepExecutionListener {

    private static final Logger logger = Logger.getLogger(TaskletImportStatuses.class);

    private final MongoTemplate mongoTemplate;
    private final StatusJpaRepository statusJpaRepository;

    private List<StatusMongo> statusMongoList;
    private List<Status> statusList;

    private static final int BATCH = 5;

    public TaskletImportStatuses(MongoTemplate mongoTemplate, StatusJpaRepository statusJpaRepository) {
        this.mongoTemplate = mongoTemplate;
        this.statusJpaRepository = statusJpaRepository;
        statusMongoList = new ArrayList<>();
        statusList = new ArrayList<>();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("TaskletImportStatuses initialized");
        statusList.clear();
        statusMongoList = mongoTemplate.findAll(StatusMongo.class);
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        try {

            for (StatusMongo statusMongo : statusMongoList)
                statusList.add(new Status(statusMongo.getName()));

            int counter = 0;
            List<Status> statusBatchList = new ArrayList<Status>();
            for (Status status : statusList) {
                statusBatchList.add(status);
                counter += 1;

                if (counter == BATCH || counter == statusList.size()) {
                    statusJpaRepository.saveAll(statusBatchList);
                    statusBatchList.clear();
                    counter = 0;
                }
            }

        } catch (Exception e) {
            logger.error("TaskletImportStatuses execute error: " + Arrays.asList(e.getStackTrace()));
            return null;
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("TaskletImportStatuses finished");
        return ExitStatus.COMPLETED;
    }
}

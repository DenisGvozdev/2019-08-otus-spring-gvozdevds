package ru.gds.spring.batch;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import ru.gds.spring.jpa.domain.Comment;
import ru.gds.spring.jpa.interfaces.CommentJpaRepository;
import ru.gds.spring.mongo.config.JobConfig;
import ru.gds.spring.mongo.config.tasklets.TaskletTablesClear;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@SpringBatchTest
@ComponentScan({"ru.gds.spring"})
@ContextConfiguration(classes = JobConfig.class)
@Import({TaskletTablesClear.class})
class SpringBatchMigrateDataBaseTest {

    private static final Logger logger = Logger.getLogger(SpringBatchMigrateDataBaseTest.class);

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private Job importDataBaseJob;

    @Autowired
    CommentJpaRepository commentJpaRepository;

    private JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeEach
    void setUp() {
        jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(importDataBaseJob);
    }

    // Перед запуском теста, в application.yml нужно отключить интерактивный режим Shell
    // spring:shell:interactive:enabled: false
    @Test
    void testJob() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        Iterator iterator = jobExecution.getStepExecutions().iterator();
        StepExecution stepExecution;
        while (iterator.hasNext()) {
            stepExecution = (StepExecution) iterator.next();
            logger.info("step " + stepExecution.getStepName() + " is " + stepExecution.getExitStatus());
            assumeTrue(ExitStatus.COMPLETED.equals(stepExecution.getExitStatus()));
        }

        List<Comment> commentList = commentJpaRepository.findAll();
        logger.info("commentList.size =  " + commentList.size());
        assumeTrue(commentList.size() > 0);
    }
}

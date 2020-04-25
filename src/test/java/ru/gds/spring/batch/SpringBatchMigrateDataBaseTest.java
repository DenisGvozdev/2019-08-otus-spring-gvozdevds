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
import ru.gds.spring.mongo.config.JobConfig;
import ru.gds.spring.mongo.config.tasklets.TaskletTablesClear;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@SpringBatchTest
@ComponentScan({"ru.gds.spring"})
@ContextConfiguration(classes  = JobConfig.class)
@Import({TaskletTablesClear.class})
class SpringBatchMigrateDataBaseTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private Job importDataBaseJob;

    @Autowired
    private BatchService batchService;

    private JobLauncherTestUtils jobLauncherTestUtils;

    @BeforeEach
    void setUp() {
        jobLauncherTestUtils = new JobLauncherTestUtils();
        jobLauncherTestUtils.setJobLauncher(jobLauncher);
        jobLauncherTestUtils.setJobRepository(jobRepository);
        jobLauncherTestUtils.setJob(importDataBaseJob);
    }

    @Test
    void testJob() throws Exception {

        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis()).toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        Assert.assertEquals(ExitStatus.COMPLETED, jobExecution.getExitStatus());

        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        assumeTrue(ExitStatus.COMPLETED.equals(stepExecution.getExitStatus()));
    }
}

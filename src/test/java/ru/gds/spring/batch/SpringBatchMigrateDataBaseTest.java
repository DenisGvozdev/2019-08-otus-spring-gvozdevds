package ru.gds.spring.batch;

import org.junit.jupiter.api.Test;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest
@SpringBatchTest
@ComponentScan({"ru.gds.spring"})
class SpringBatchMigrateDataBaseTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    void testJob() {
        JobParameters jobParameters =
                new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis()).toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchStep("stepAuthors", jobParameters);

        StepExecution stepExecution = jobExecution.getStepExecutions().iterator().next();
        assumeTrue(ExitStatus.COMPLETED.equals(stepExecution.getExitStatus()));
    }
}

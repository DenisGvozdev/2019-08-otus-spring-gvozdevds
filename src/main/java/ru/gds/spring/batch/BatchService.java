package ru.gds.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class BatchService {

    private JobLauncher jobLauncher;

    private JobOperator jobOperator;

    @Qualifier("importAuthorJob")
    private Job importAuthorJob;

    @Qualifier("importGenreJob")
    private Job importGenreJob;

    @Qualifier("importStatusJob")
    private Job importStatusJob;

    @Qualifier("importCommentJob")
    private Job importCommentJob;

    @Qualifier("importBookJob")
    private Job importBookJob;

    BatchService(
            JobLauncher jobLauncher,
            JobOperator jobOperator,
            Job importAuthorJob,
            Job importGenreJob,
            Job importStatusJob,
            Job importBookJob,
            Job importCommentJob) {

        this.jobLauncher = jobLauncher;
        this.jobOperator = jobOperator;
        this.importAuthorJob = importAuthorJob;
        this.importGenreJob = importGenreJob;
        this.importStatusJob = importStatusJob;
        this.importBookJob = importBookJob;
        this.importCommentJob = importCommentJob;

    }

    /* ------ Работаем с авторами ------ */

    public void launchImportAuthorJob() {
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis()).toJobParameters();

            jobLauncher.run(importAuthorJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopImportAuthorJob() {
        try {
            Set<Long> executions = jobOperator.getRunningExecutions("importAuthorJob");
            jobOperator.stop(executions.iterator().next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ------ Работаем с жанрами ------ */

    public void launchImportGenreJob() {
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(importGenreJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopImportGenreJob() {
        try {
            Set<Long> executions = jobOperator.getRunningExecutions("importGenreJob");
            jobOperator.stop(executions.iterator().next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ------ Работаем со статусами ------ */

    public void launchImportStatusJob() {
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(importStatusJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopImportStatusJob() {
        try {
            Set<Long> executions = jobOperator.getRunningExecutions("importStatusJob");
            jobOperator.stop(executions.iterator().next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ------ Работаем с книгами ------ */

    public void launchImportBookJob() {
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(importBookJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopImportBookJob() {
        try {
            Set<Long> executions = jobOperator.getRunningExecutions("importBookJob");
            jobOperator.stop(executions.iterator().next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* ------ Работаем с комментариями ------ */

    public void launchImportCommentJob() {
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis()).toJobParameters();
            jobLauncher.run(importCommentJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopImportCommentJob() {
        try {
            Set<Long> executions = jobOperator.getRunningExecutions("importCommentJob");
            jobOperator.stop(executions.iterator().next());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

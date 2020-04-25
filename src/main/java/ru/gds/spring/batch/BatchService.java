package ru.gds.spring.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    private JobLauncher jobLauncher;

    @Qualifier("importDataBaseJob")
    private Job importDataBaseJob;

    BatchService(JobLauncher jobLauncher, Job importDataBaseJob) {
        this.jobLauncher = jobLauncher;
        this.importDataBaseJob = importDataBaseJob;
    }

    public void launchImportDataBseJob() {
        try {
            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addLong("time", System.currentTimeMillis()).toJobParameters();

            jobLauncher.run(importDataBaseJob, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

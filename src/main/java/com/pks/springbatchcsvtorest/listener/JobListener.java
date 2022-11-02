package com.pks.springbatchcsvtorest.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JobListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED)
            log.info("---DONE---");
        else
            log.error("---FAILED--- Check your logs");
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("Preparing Job");
    }
}

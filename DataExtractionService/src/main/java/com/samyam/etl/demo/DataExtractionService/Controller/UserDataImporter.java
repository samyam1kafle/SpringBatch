package com.samyam.etl.demo.DataExtractionService.Controller;

import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : Samyam Kafle
 * @Portfolio : https://samyamkafle.com.np
 * @Project : ETLMS
 * @CreatedDate : 08/12/2024, Sunday
 **/
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserDataImporter {
    private static final Logger logger = LoggerFactory.getLogger(UserDataImporter.class);
    private final Job job;

    private final JobLauncher jobLauncher;

    @PostMapping("/import-from-csv")
    public ResponseEntity<HashMap<String, Object>> importUserDataFromCSV(){
        logger.info("import-from-csv api called");
        HashMap<String, Object> response = new HashMap<>(
                Map.of("success", false,
                        "message", "Error Loading Data"
                )
        );

        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job,jobParameters);
            response.put("success",true);
            response.put("message", "Data loaded successfully");

            return ResponseEntity.status(HttpStatus.SC_OK).body(response);
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
            response.put("message", "Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(response);
        }
    }
}

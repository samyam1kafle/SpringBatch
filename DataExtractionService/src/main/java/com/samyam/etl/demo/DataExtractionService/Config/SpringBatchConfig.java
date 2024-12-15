package com.samyam.etl.demo.DataExtractionService.Config;

import com.samyam.etl.demo.DataExtractionService.Mapper.UserFieldSetMapper;
import com.samyam.etl.demo.DataExtractionService.Models.User;
import com.samyam.etl.demo.DataExtractionService.Processors.UserDataProcessor;
import com.samyam.etl.demo.DataExtractionService.Repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;

import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;



/**
 * @Author : Samyam Kafle
 * @Portfolio : https://samyamkafle.com.np
 * @Project : ETLMS
 * @CreatedDate : 02/12/2024, Monday
 **/
@Configuration
@RequiredArgsConstructor
public class SpringBatchConfig  {

    private static final Logger logger = LoggerFactory.getLogger(SpringBatchConfig.class);
    private final UserRepository userRepository;
    private final JobRepository jobRepository;

    @Value("${custom.userDataFileName}")
    private String userFileName;
    private final PlatformTransactionManager platformTransactionManager;

    // Item Reader
    @Bean
    public FlatFileItemReader<User> itemReader(){
        logger.info("Item Reader: " );
        logger.info("File Name: {} ", userFileName );

        FlatFileItemReader<User> itemReader = new FlatFileItemReader<>();
        String filePath = "data/"+userFileName;
        itemReader.setResource(new ClassPathResource(filePath));
        logger.info("Item Reader File Resource Path:{} ", new ClassPathResource(filePath));
        itemReader.setName("studentDataReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    private LineMapper<User> lineMapper() {
        DefaultLineMapper<User> lineMapper = new DefaultLineMapper<>();

        // Setup delimeted Line tokenizer for processing the csv data
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);

        // The names must match with the one defined on the entity class.
        delimitedLineTokenizer.setNames("id", "customerId", "firstName", "lastName", "company", "city",
                "country", "phone1", "phone2", "email", "subscriptionDate", "Website");

//        // The beanWrapperFieldSetMapper set's the values from the csv file to our pojo class.
//        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//        fieldSetMapper.setTargetType(User.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(new UserFieldSetMapper());

        return lineMapper;
    }

    // Item Processor
    @Bean
    public UserDataProcessor processor(){
        return new UserDataProcessor();
    }

    // Item Writer
    @Bean
    public RepositoryItemWriter<User> writer(){
        RepositoryItemWriter<User> writer = new RepositoryItemWriter<>();
        writer.setRepository(userRepository);
        writer.setMethodName("save");
        return writer;
    }
    /*
    * Configuration for Step Builder
    * */
    @Bean
    public Step importStep(){
        return new StepBuilder("studentDataReader",jobRepository)
                .<User,User>chunk(5000, platformTransactionManager)
                .reader(itemReader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor())
                .build();
    }

    /*
    * Configuration for Job setup
    * */
    @Bean
    public Job runJob(){
        return new JobBuilder("importUsersData", jobRepository)
                .start(importStep())
                .build();
    }

    //  For setting up async task to improve the performance
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(20);
        return asyncTaskExecutor;
    }
}

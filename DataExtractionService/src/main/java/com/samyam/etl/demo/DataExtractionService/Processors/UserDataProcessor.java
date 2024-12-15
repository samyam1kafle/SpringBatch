package com.samyam.etl.demo.DataExtractionService.Processors;

import com.samyam.etl.demo.DataExtractionService.Models.User;
import org.springframework.batch.item.ItemProcessor;

/**
 * @Author : Samyam Kafle
 * @Portfolio : https://samyamkafle.com.np
 * @Project : ETLMS
 * @CreatedDate : 08/12/2024, Sunday
 **/
public class UserDataProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) throws Exception {
        // Transformation / business logic goes here
        return user;
    }
}

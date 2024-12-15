package com.samyam.etl.demo.DataExtractionService.Mapper;

import com.samyam.etl.demo.DataExtractionService.Models.User;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Author : Samyam Kafle
 * @Portfolio : https://samyamkafle.com.np
 * @Project : ETLMS
 * @CreatedDate : 13/12/2024, Friday
 **/
public class UserFieldSetMapper implements FieldSetMapper<User> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public User mapFieldSet(FieldSet fieldSet) throws BindException {
        User user = new User();
        user.setId(fieldSet.readInt("id"));
        user.setCustomerId(fieldSet.readString("customerId"));
        user.setFirstName(fieldSet.readString("firstName"));
        user.setLastName(fieldSet.readString("lastName"));
        user.setCompany(fieldSet.readString("company"));
        user.setCity(fieldSet.readString("city"));
        user.setCountry(fieldSet.readString("country"));
        user.setPhone1(fieldSet.readString("phone1"));
        user.setPhone2(fieldSet.readString("phone2"));
        user.setEmail(fieldSet.readString("email"));
        user.setSubscriptionDate(LocalDate.parse(fieldSet.readString("subscriptionDate"),DATE_FORMATTER));
        user.setWebsite(fieldSet.readString("Website"));
        return user;
    }
}

package com.samyam.etl.demo.DataExtractionService.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author : Samyam Kafle
 * @Portfolio : https://samyamkafle.com.np
 * @Project : ETLMS
 * @CreatedDate : 02/12/2024, Monday
 **/
@Entity
@Table(name = "USER_DATA")
@Data
public class User {
    @Id
    private Integer id;
    private String customerId;
    private String firstName;
    private String lastName;
    private String company;
    private String city;
    private String country;
    private String phone1;
    private String phone2;
    private String email;
    private LocalDate subscriptionDate;
    private String website;
}

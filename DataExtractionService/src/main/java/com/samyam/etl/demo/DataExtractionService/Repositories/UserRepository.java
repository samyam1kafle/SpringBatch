package com.samyam.etl.demo.DataExtractionService.Repositories;

import com.samyam.etl.demo.DataExtractionService.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author : Samyam Kafle
 * @Portfolio : https://samyamkafle.com.np
 * @Project : ETLMS
 * @CreatedDate : 02/12/2024, Monday
 **/
public interface UserRepository extends JpaRepository<User,Integer> { }

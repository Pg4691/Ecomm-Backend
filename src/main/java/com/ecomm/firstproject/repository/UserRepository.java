package com.ecomm.firstproject.repository;

import com.ecomm.firstproject.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByEmailId(String emailId);
}
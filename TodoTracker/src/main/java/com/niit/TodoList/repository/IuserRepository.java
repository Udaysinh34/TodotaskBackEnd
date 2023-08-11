package com.niit.TodoList.repository;

import com.niit.TodoList.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IuserRepository extends MongoRepository<User,String> {
    User findByEmail(String email);
}

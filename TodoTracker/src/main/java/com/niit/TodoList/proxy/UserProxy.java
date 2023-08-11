package com.niit.TodoList.proxy;

import com.niit.TodoList.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-authentication-service",url = "localhost:8086")
public interface UserProxy {
    @PostMapping("/app-v1/register")
    ResponseEntity<?>saveUser(@RequestBody User user);
}

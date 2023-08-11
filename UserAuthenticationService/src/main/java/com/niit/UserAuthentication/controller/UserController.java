package com.niit.UserAuthentication.controller;

import com.niit.UserAuthentication.domain.User;
import com.niit.UserAuthentication.exception.UserAlreadyFoundExcetion;
import com.niit.UserAuthentication.exception.UserNotFoundExcetion;
import com.niit.UserAuthentication.service.SecurityTokenGenerator;
import com.niit.UserAuthentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/app-v1/")
//@CrossOrigin
public class UserController {
    private SecurityTokenGenerator securityTokenGenerator;
    private UserService userService;
    @Autowired
    public UserController(SecurityTokenGenerator securityTokenGenerator, UserService userService) {
        this.securityTokenGenerator = securityTokenGenerator;
        this.userService = userService;
    }
    @PostMapping("register")
    public ResponseEntity<?> saveUser(@RequestBody User user)throws UserAlreadyFoundExcetion{
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }
    @PostMapping("login")
    public ResponseEntity<?>loginUSer(@RequestBody User user)throws UserNotFoundExcetion{
        Map<String,String>map=null;
        try{
            User user1=userService.getUserByEmailAndPassword(user.getEmail(), user.getPassword());
            if (user1.getEmail().equals(user.getEmail())){
                map=securityTokenGenerator.generateToken(user);
            }
            return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
        }catch (UserNotFoundExcetion e){
            throw new RuntimeException(e);
        }catch (Exception e){
            return new ResponseEntity<>("Try after Sometime",HttpStatus.GATEWAY_TIMEOUT);
        }
    }
    @GetMapping("Users")
    public ResponseEntity<?>getAllUsers(){
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("user/{email}")
    public ResponseEntity<?>deleteUser(@PathVariable String email) throws UserNotFoundExcetion {
        return new ResponseEntity<>(userService.deleteUSer(email),HttpStatus.ACCEPTED);

    }
    @PutMapping("users/{email}")
    public ResponseEntity<?>updateUSer(@RequestBody User user,@PathVariable String email){
    return new ResponseEntity<>(userService.updateUSer(user,email),HttpStatus.CREATED);
    }

}

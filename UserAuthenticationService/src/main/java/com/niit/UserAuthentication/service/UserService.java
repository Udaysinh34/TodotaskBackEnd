package com.niit.UserAuthentication.service;

import com.niit.UserAuthentication.domain.User;
import com.niit.UserAuthentication.exception.UserAlreadyFoundExcetion;
import com.niit.UserAuthentication.exception.UserNotFoundExcetion;

import java.util.List;

public interface UserService {
    public User saveUser(User user)throws UserAlreadyFoundExcetion;
    public User getUserByEmailAndPassword(String email,String password)throws UserNotFoundExcetion;
    public List<User>getAllUser();
    public String deleteUSer(String email) throws UserNotFoundExcetion;
    public User updateUSer(User user,String email);
}

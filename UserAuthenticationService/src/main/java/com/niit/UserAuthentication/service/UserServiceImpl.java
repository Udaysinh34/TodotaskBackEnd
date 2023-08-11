package com.niit.UserAuthentication.service;

import com.niit.UserAuthentication.domain.User;
import com.niit.UserAuthentication.exception.UserAlreadyFoundExcetion;
import com.niit.UserAuthentication.exception.UserNotFoundExcetion;
import com.niit.UserAuthentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    public UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) throws UserAlreadyFoundExcetion {
        if (userRepository.findById(user.getEmail()).isPresent()){
            throw new UserAlreadyFoundExcetion();

        }
        return userRepository.save(user);

    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) throws UserNotFoundExcetion {
        User user=userRepository.findByEmailAndPassword(email, password);
        if (user==null){
            throw new UserNotFoundExcetion();
        } else if (password.equals(user.getPassword())) {
            return user;
        }
        else {
            throw new UserNotFoundExcetion();
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public String deleteUSer(String email) throws UserNotFoundExcetion {
        if (userRepository.findById(email).isPresent()){
            throw new UserNotFoundExcetion();
        }else {
            userRepository.deleteById(email);
            return "User deleted Successfully";
        }

    }

    @Override
    public User updateUSer(User user, String email) {
        Optional<User>optional=userRepository.findById(email);
        if (optional.isEmpty()){
            return null;
        }
        User result=optional.get();
        if (user.getFirstName()!=null){
            result.setFirstName(user.getFirstName());
        } else if (user.getLastName()!=null) {
            result.setLastName(user.getLastName());
        } else if (user.getPassword()!=null) {
            result.setPassword(user.getPassword());

        }
        return userRepository.save(result);
    }
}


















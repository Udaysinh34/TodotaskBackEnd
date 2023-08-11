package com.niit.TodoList.service;

import com.niit.TodoList.domain.ToDoTask;
import com.niit.TodoList.domain.User;
import com.niit.TodoList.exceptions.UserAlreadyExistsException;
import com.niit.TodoList.exceptions.UserNotFoundException;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IUserService {
    public boolean sendOtp(String email) throws UserAlreadyExistsException;
    public User registerUser(User user) throws UserAlreadyExistsException, ExecutionException, InterruptedException;
    public User addTask(String current_email,ToDoTask todo)throws UserNotFoundException;
    public int generateId(String current_email);


    public Boolean removeTask(String current_email,int todoId);

    public ToDoTask getTask(String current_email, int todoTitle);

    public List<ToDoTask> getAllTasks(String current_email);

    public User modifyTask(String current_email, ToDoTask toDoTask) throws UserNotFoundException;
    public User getUser(String current_email) throws UserNotFoundException;
    public User updateProfileImage(String current_email,String profileImage);
}

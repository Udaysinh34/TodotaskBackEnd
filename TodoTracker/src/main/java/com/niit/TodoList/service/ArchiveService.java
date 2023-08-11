package com.niit.TodoList.service;

import com.niit.TodoList.domain.ToDoTask;
import com.niit.TodoList.domain.User;
import com.niit.TodoList.exceptions.UserNotFoundException;

import java.util.List;

public interface ArchiveService
{
    public User addTask(String current_email, int todoId)throws UserNotFoundException;


    public Boolean removeTask(String current_email,int todoId);

    public ToDoTask getTask(String current_email, int todoId);
    public Boolean unArchiveTask(String current_email,int todoId)throws UserNotFoundException;
    public List<ToDoTask> getAllArchives(String current_email);

    public User modifyTask(String current_email, int todoId) throws UserNotFoundException;


}

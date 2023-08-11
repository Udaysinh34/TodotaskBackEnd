package com.niit.TodoList.service;

import com.niit.TodoList.domain.ToDoTask;
import com.niit.TodoList.domain.User;
import com.niit.TodoList.exceptions.UserNotFoundException;
import com.niit.TodoList.proxy.UserProxy;
import com.niit.TodoList.repository.IuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CompletedTaskServiceImpl implements CompletedTaskService
{
    private IuserRepository iuserRepository;

    @Autowired
    public CompletedTaskServiceImpl(IuserRepository iuserRepository, UserProxy userProxy) {
        this.iuserRepository = iuserRepository;
    }

    @Override
    public User addTask(String current_email, int todoId) throws UserNotFoundException {
//        User user = iuserRepository.findById(current_email).get();
//        user.getCompletedTask().add(todo);
//        return iuserRepository.save(user);
        if(iuserRepository.findById(current_email).isEmpty()){
            throw new UserNotFoundException();
        }
        User user=iuserRepository.findById(current_email).get();
        if (user.getCompletedTask()==null){
            user.setCompletedTask(new ArrayList<>());
        }else {
            List<ToDoTask> todoTasks=user.getToDoTask();

            List<ToDoTask> completedTask= user.getCompletedTask();
            Iterator<ToDoTask> iterator=todoTasks.listIterator();
            while (iterator.hasNext()){
                ToDoTask itr=iterator.next();
                if (itr.getTodoId()==todoId){
                    completedTask.add(itr);
                    iterator.remove();
                }
            }
            user.setToDoTask(new ArrayList<>());
            user.setToDoTask(todoTasks);
            System.out.println(completedTask);
            user.setCompletedTask(completedTask);

        }
        return iuserRepository.save(user);

    }

    @Override
    public ToDoTask getTask(String current_email, int todoId) {
        ToDoTask task = null;

        User user = iuserRepository.findById(current_email).get();
        List<ToDoTask> completedTaskList = user.getCompletedTask();
        Iterator<ToDoTask> iterator = completedTaskList.listIterator();
        while (iterator.hasNext()) {
            ToDoTask i = iterator.next();
            if (i.getTodoId() == todoId) {
                task = i;
            }

        }return task;
    }

    @Override
    public List<ToDoTask> getAllCompleted(String current_email) {
        return iuserRepository.findById(current_email).get().getCompletedTask();
    }


    @Override
    public Boolean removeTask(String current_email, int todoId) {
        User user = iuserRepository.findById(current_email).get();
        List<ToDoTask> completedTaskList = user.getCompletedTask();
        Iterator<ToDoTask> iterator = completedTaskList.listIterator();
        while (iterator.hasNext()) {
            ToDoTask i = iterator.next();
            if (i.getTodoId() == todoId) {
                completedTaskList.remove(i);
                user.setCompletedTask(completedTaskList);
                iuserRepository.save(user);
                return true;
            }
        }
        return false;
    }


    @Override
    public User modifyTask(String current_email, int todoId) throws UserNotFoundException {
        removeTask(current_email, todoId);
        addTask(current_email,todoId);
        User user = iuserRepository.findById(current_email).get();
        return user;
    }

}

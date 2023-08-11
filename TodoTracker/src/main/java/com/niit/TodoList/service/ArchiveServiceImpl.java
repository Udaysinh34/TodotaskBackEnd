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
public class ArchiveServiceImpl implements ArchiveService
{
    private IuserRepository iuserRepository;

    @Autowired
    public ArchiveServiceImpl(IuserRepository iuserRepository, UserProxy userProxy) {
        this.iuserRepository = iuserRepository;
    }

    @Override
    public User addTask(String current_email, int todoId) throws UserNotFoundException {
//        User user = iuserRepository.findById(current_email).get();
//        user.getArchievedTask().add(todo);
//        return iuserRepository.save(user);
        System.out.println(todoId);

        if(iuserRepository.findById(current_email).isEmpty()){
            throw new UserNotFoundException();
        }
        User user=iuserRepository.findById(current_email).get();
        if (user.getArchievedTask()==null){
            user.setArchievedTask(new ArrayList<>());
        }else {
            List<ToDoTask> archivedTodos=user.getArchievedTask();
            List<ToDoTask> toDoTasks=user.getToDoTask();
            Iterator<ToDoTask>iterator=toDoTasks.listIterator();
            while (iterator.hasNext()){
                ToDoTask itr=iterator.next();
                if (itr.getTodoId()==todoId){
//                    int archId = itr.getTodoId();
//                    itr.setTodoId(archId+1000);
                    archivedTodos.add(itr);
                    iterator.remove();
                }
            }
            user.setToDoTask(new ArrayList<>());
            user.setToDoTask(toDoTasks);
        }
        return iuserRepository.save(user);

    }

    @Override
    public ToDoTask getTask(String current_email, int todoId) {
        ToDoTask task = null;

        User user = iuserRepository.findById(current_email).get();
        List<ToDoTask> archiveList = user.getArchievedTask();
        Iterator<ToDoTask> iterator = archiveList.listIterator();
        while (iterator.hasNext()) {
            ToDoTask i = iterator.next();
            if (i.getTodoId() == todoId) {
                task = i;
            }

        }return task;
    }
    @Override
    public Boolean unArchiveTask(String current_email, int todoId) throws UserNotFoundException {
        if (iuserRepository.findById(current_email).isEmpty()){
            throw new UserNotFoundException();
        }
        User user=iuserRepository.findById(current_email).get();
        if (user.getToDoTask()==null){
            user.setToDoTask(new ArrayList<>());
        }else {
            List<ToDoTask> allTodoTasks=user.getToDoTask();
            List<ToDoTask> allArchiveTaks=user.getArchievedTask();
            Iterator<ToDoTask> it=allArchiveTaks.listIterator();
            while (it.hasNext()){
                ToDoTask unArchive =it.next();
                if (unArchive.getTodoId()==todoId){
                    allTodoTasks.add(unArchive);
                    it.remove();
                }
            }
            user.setToDoTask(new ArrayList<>());
            user.setToDoTask(allTodoTasks);
            user.setArchievedTask(new ArrayList<>());
            user.setArchievedTask(allArchiveTaks);
            iuserRepository.save(user);

        }
        return true;
    }

    @Override
    public List<ToDoTask> getAllArchives(String current_email) {
        return iuserRepository.findById(current_email).get().getArchievedTask();
    }


    @Override
    public Boolean removeTask(String current_email, int todoId) {
        User user = iuserRepository.findById(current_email).get();
        List<ToDoTask> archiveList = user.getArchievedTask();
        Iterator<ToDoTask> iterator = archiveList.listIterator();
        while (iterator.hasNext()) {
            ToDoTask i = iterator.next();
            if (i.getTodoId() == todoId) {
                archiveList.remove(i);
                user.setArchievedTask(archiveList);
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


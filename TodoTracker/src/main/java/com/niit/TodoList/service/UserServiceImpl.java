package com.niit.TodoList.service;

import com.niit.TodoList.domain.EmailData;
import com.niit.TodoList.domain.ToDoTask;
import com.niit.TodoList.domain.User;
import com.niit.TodoList.exceptions.UserAlreadyExistsException;
import com.niit.TodoList.exceptions.UserNotFoundException;
import com.niit.TodoList.proxy.MailProxy;
import com.niit.TodoList.proxy.UserProxy;
import com.niit.TodoList.repository.IuserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements IUserService {

    private IuserRepository iuserRepository;
    private UserProxy userProxy;
    private MailProxy mailProxy;
    EmailData emailData = new EmailData() ;

    int id;

    @Autowired
    public UserServiceImpl(IuserRepository iuserRepository, UserProxy userProxy, MailProxy mailProxy) {
        this.iuserRepository = iuserRepository;
        this.userProxy = userProxy;
        this.mailProxy = mailProxy;
    }

    @Override
    public boolean sendOtp(String email) throws UserAlreadyExistsException{
        if (iuserRepository.findById(email).isPresent()) {
            throw new UserAlreadyExistsException();
        }
        Random random = new Random();
        int max=999999,min=100000;
        int otp = random.nextInt(max - min + 1) + min;

        emailData.setReceiver(email);
        emailData.setSubject("Otp for sign up");
        emailData.setMessage("Checkmate - the todo Tracker App ...\n Your OTP is "+otp);
        emailData.setOtp(otp);
        try{
            mailProxy.sendOtp(emailData);
            return true;
        }catch(Exception e){
            System.out.println(e);
        }
        return true;
    }
    @Override
    public User registerUser(User user) throws UserAlreadyExistsException{
        if (iuserRepository.findById(user.getEmail()).isPresent() || user.getOtp()!=emailData.getOtp()) {
            throw new UserAlreadyExistsException();
        }


        try{
        if(user.getOtp()==emailData.getOtp()){

            emailData.setReceiver(user.getEmail());
            emailData.setSubject("Sign up successful");
            emailData.setMessage("Welcome to Checkmate - the todo Tracker App ");

            userProxy.saveUser(user);
            User register =  iuserRepository.save(user);


                mailProxy.sendEmail(emailData);
                return register;
            }
        else{
            throw new UserAlreadyExistsException();
        }
        }
        catch(Exception e){
            System.out.println(e);
        }

     return null;
    }


    @Override
    public User addTask(String current_email, ToDoTask todo) throws UserNotFoundException {
        User user = iuserRepository.findById(current_email).get();
        List<ToDoTask> todolist = user.getToDoTask();
        todo.setTodoId(generateId(current_email));
//        String title=todo.getTodoTitle();
//        todo.setTodoTitle(title.toUpperCase());
        todolist.add(todo);
        user.setToDoTask(todolist);
        return iuserRepository.save(user);
    }

    @Override
    public ToDoTask getTask(String current_email, int todoId) {
        ToDoTask task = null;

        User user = iuserRepository.findById(current_email).get();
        List<ToDoTask> todolist = user.getToDoTask();
        Iterator<ToDoTask> iterator = todolist.listIterator();
        while (iterator.hasNext()) {
            ToDoTask i = iterator.next();
            if (i.getTodoId() == todoId) {
                task = i;
            }

        }return task;
    }

    @Override
    public int generateId(String current_email){

        int id2;
        id=id+1;
        id2 = id;

        return id2;
    }

    @Override
    public List<ToDoTask> getAllTasks(String current_email)
    {
        return iuserRepository.findById(current_email).get().getToDoTask();

    }


    @Override
    public Boolean removeTask(String current_email, int todoId) {
        User user = iuserRepository.findById(current_email).get();

        List<ToDoTask> todolist = user.getToDoTask();
        Iterator<ToDoTask> iterator = todolist.listIterator();
        while (iterator.hasNext()) {
            ToDoTask i = iterator.next();
            System.out.println(i.getTodoId());
            if (i.getTodoId() == todoId) {
             //   iterator.remove();
               todolist.remove(i);

                user.setToDoTask(todolist);
                iuserRepository.save(user);
                return true;

            }
          //  user.setToDoTask(new ArrayList<>());
           // user.setToDoTask(todolist);
        }
        return false;
    }


    @Override
    public User modifyTask(String current_email, ToDoTask toDoTask) throws UserNotFoundException {
//        removeTask(current_email, toDoTask.getTodoId());
//        addTask(current_email,toDoTask);

        User user = iuserRepository.findById(current_email).get();
        List<ToDoTask> todolist = user.getToDoTask();
        Iterator<ToDoTask> iterator = todolist.listIterator();
        while (iterator.hasNext()) {
            ToDoTask i = iterator.next();


            if (i.getTodoId() == toDoTask.getTodoId()) {
                i.setTodoTitle(toDoTask.getTodoTitle());
                i.setTodoDescription(toDoTask.getTodoDescription());

                i.setCategory(toDoTask.getCategory());
                i.setDueDate(toDoTask.getDueDate());
                i.setListOfTodo(toDoTask.getListOfTodo());
                i.setBackColour(toDoTask.getBackColour());

                user.setToDoTask(todolist);
                iuserRepository.save(user);
                return user;

            }

        }
        return user;
    }
    @Override
    public User getUser(String current_email)throws UserNotFoundException{
        User user = iuserRepository.findById(current_email).get();
        return user;
    }

    public User updateProfileImage(String current_email,String profileImage){
        User user = iuserRepository.findById(current_email).get();
        user.setProfileImage(profileImage);
        iuserRepository.save(user);
        return user;
    }
}

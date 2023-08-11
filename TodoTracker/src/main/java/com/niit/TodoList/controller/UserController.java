package com.niit.TodoList.controller;

import com.niit.TodoList.domain.ToDoTask;
import com.niit.TodoList.domain.User;
import com.niit.TodoList.exceptions.UserAlreadyExistsException;
import com.niit.TodoList.exceptions.UserNotFoundException;
import com.niit.TodoList.service.ArchiveService;
import com.niit.TodoList.service.CompletedTaskService;
import com.niit.TodoList.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/todo-app")
//@CrossOrigin
public class UserController {
    private IUserService iUserService;
    private ArchiveService archiveService;
    private CompletedTaskService completedTaskService;

    @Autowired
    public UserController(IUserService iUserService,ArchiveService archiveService , CompletedTaskService completedTaskService) {
        this.iUserService = iUserService;
        this.archiveService = archiveService;
        this.completedTaskService = completedTaskService;
    }

    @PostMapping("/send-otp/{email}")
    public ResponseEntity<?>sendOtp(@PathVariable String email)throws UserAlreadyExistsException{
        ResponseEntity responseEntity=null;
        System.out.println(email);
        try {

            responseEntity=new ResponseEntity<>(iUserService.sendOtp(email), HttpStatus.CREATED);
        }catch (UserAlreadyExistsException e){
            throw new UserAlreadyExistsException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PostMapping("/register")
    public ResponseEntity<?>addUser(@RequestBody User user )throws UserAlreadyExistsException{
        ResponseEntity responseEntity=null;
        try {
            user.setToDoTask(new ArrayList<ToDoTask>());
            user.setArchievedTask(new ArrayList<ToDoTask>());
            user.setCompletedTask(new ArrayList<ToDoTask>());
            user.setProfileImage("data:image/jpeg;base64,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");
            responseEntity=new ResponseEntity<>(iUserService.registerUser(user), HttpStatus.CREATED);
        }catch (UserAlreadyExistsException e){
            throw new UserAlreadyExistsException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PostMapping("/update-profile-image")
    public ResponseEntity<?>updateProfileImage(@RequestBody String profileImage, HttpServletRequest request)throws UserNotFoundException {
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");

        try{
//            toDoTask.setTodoId(iUserService.generateId(current_email));
            responseEntity=new ResponseEntity<>(iUserService.updateProfileImage(current_email,profileImage),HttpStatus.OK);
        }catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.OK);
        }
        return responseEntity;
    }
    @GetMapping("/get-user")
    public ResponseEntity<?>getUser(HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");

        try{
            responseEntity=new ResponseEntity<>(iUserService.getUser(current_email),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PostMapping("/add-task")
    public ResponseEntity<?>addTask(@RequestBody ToDoTask toDoTask, HttpServletRequest request)throws UserNotFoundException {
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");

        try{
//            toDoTask.setTodoId(iUserService.generateId(current_email));
            responseEntity=new ResponseEntity<>(iUserService.addTask(current_email,toDoTask),HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.OK);
        }
        return responseEntity;
    }

    @GetMapping("/get-task/{todoId}")
    public ResponseEntity<?>getTaskItem(@PathVariable int todoId ,HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");

        try{
            responseEntity=new ResponseEntity<>(iUserService.getTask(current_email,todoId),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @GetMapping("/get-all-task")
    public ResponseEntity<?>getAllTask(HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");

        try{
            responseEntity=new ResponseEntity<>(iUserService.getAllTasks(current_email),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @DeleteMapping("/delete-task/{todoId}")
    public ResponseEntity<?>deleteTask(@PathVariable int todoId, HttpServletRequest request)throws UserNotFoundException {
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");


        try{
            responseEntity=new ResponseEntity<>(iUserService.removeTask(current_email,todoId),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.OK);
        }
        return responseEntity;
    }



    @PutMapping("/update")
    public ResponseEntity<?>modifyList(@RequestBody ToDoTask toDoTask, HttpServletRequest request)throws UserNotFoundException {
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(iUserService.modifyTask(current_email,toDoTask),HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }



    //archived task


    @PostMapping("/add-archived-task")
    public ResponseEntity<?>addArchivedTask(@RequestBody  int todoId, HttpServletRequest request)throws UserNotFoundException {
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(archiveService.addTask(current_email,todoId),HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/get-archived-task/{todoId}")
    public ResponseEntity<?>getArchivedTaskItem(@PathVariable int todoId ,HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(archiveService.getTask(current_email,todoId),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/get-all-archived")
    public ResponseEntity<?>getAllArchived(HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(archiveService.getAllArchives(current_email),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    @DeleteMapping("/delete-archived-task/{todoId}")
    public ResponseEntity<?>removeArchivedTaskItem(@PathVariable int todoId ,HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(archiveService.removeTask(current_email,todoId),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @PostMapping("/Unarchived")
    public ResponseEntity<?>UnArchived(@RequestBody int todoId,  HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(archiveService.unArchiveTask(current_email,todoId),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    @PutMapping("/update-archived-task")
    public ResponseEntity<?>modifyArchivedList(@RequestBody int  todoId, HttpServletRequest request)throws UserNotFoundException {
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(archiveService.modifyTask(current_email,todoId),HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    //CompletedList

    @PostMapping("/add-completed-task")
    public ResponseEntity<?>addcompletedTask(@RequestBody int todoId, HttpServletRequest request)throws UserNotFoundException {
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(completedTaskService.addTask(current_email,todoId),HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/get-completed-task/{todoId}")
    public ResponseEntity<?>getCompletedTaskItem(@PathVariable int todoId ,HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(completedTaskService.getTask(current_email,todoId),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @GetMapping("/get-all-completed")
    public ResponseEntity<?>getAllCompleted(HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(completedTaskService.getAllCompleted(current_email),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }



    @DeleteMapping("/delete-completed-task/{todoId}")
    public ResponseEntity<?>removecompletedTaskItem(@PathVariable int todoId ,HttpServletRequest request)throws UserNotFoundException{
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(completedTaskService.removeTask(current_email,todoId),HttpStatus.OK);
        } catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }


    @PutMapping("/update-completed-task")
    public ResponseEntity<?>modifyCompletedList(@RequestBody int todoId, HttpServletRequest request)throws UserNotFoundException {
        ResponseEntity responseEntity=null;
        String current_email = (String) request.getAttribute("Current_user_emailId");
        try{
            responseEntity=new ResponseEntity<>(completedTaskService.modifyTask(current_email,todoId),HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
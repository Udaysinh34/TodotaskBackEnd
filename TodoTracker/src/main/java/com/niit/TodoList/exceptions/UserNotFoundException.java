package com.niit.TodoList.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,reason = "User Doesn't Exits")
public class UserNotFoundException extends Exception{
}

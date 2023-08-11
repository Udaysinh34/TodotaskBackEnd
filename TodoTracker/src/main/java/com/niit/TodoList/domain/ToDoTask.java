package com.niit.TodoList.domain;

import java.util.List;


public class ToDoTask {

    private int todoId;
    private String todoTitle;
    private String todoDescription;
    private String category;
    private String createdDate;
    private String dueDate;
    private boolean favourite;
    private List<String> listOfTodo;
    private String backColour;


    public ToDoTask() {
    }


    public ToDoTask(int todoId, String todoTitle, String todoDescription, String category, String createdDate, String dueDate, boolean favourite, List<String> listOfTodo, String backColour) {
        this.todoId = todoId;
        this.todoTitle = todoTitle;
        this.todoDescription = todoDescription;
        this.category = category;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.favourite = favourite;
        this.listOfTodo = listOfTodo;
        this.backColour = backColour;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getTodoDescription() {
        return todoDescription;
    }

    public void setTodoDescription(String todoDescription) {
        this.todoDescription = todoDescription;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public List<String> getListOfTodo() {
        return listOfTodo;
    }

    public void setListOfTodo(List<String> listOfTodo) {
        this.listOfTodo = listOfTodo;
    }

    public String getBackColour() {
        return backColour;
    }

    public void setBackColour(String backColour) {
        this.backColour = backColour;
    }

    @Override
    public String toString() {
        return "ToDoTask{" +
                "todoId=" + todoId +
                ", todoTitle='" + todoTitle + '\'' +
                ", todoDescription='" + todoDescription + '\'' +
                ", category='" + category + '\'' +
                ", createdDate='" + createdDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", favourite=" + favourite +
                ", listOfTodo=" + listOfTodo +
                ", backColour='" + backColour + '\'' +
                '}';
    }
}

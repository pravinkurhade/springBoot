package com.springmongo.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.springmongo.exception.TodoCollectionException;
import com.springmongo.model.TodoDTO;

public interface TodoService {

	public void createTodo(TodoDTO todo) throws TodoCollectionException, ConstraintViolationException;
	
	public List<TodoDTO> getAllTodos();
	
	public TodoDTO getSingleTodo(String id) throws TodoCollectionException;
	
	public void updateTodo(TodoDTO todo) throws TodoCollectionException;
	
	public void deleteTodoById(String id) throws TodoCollectionException;
}

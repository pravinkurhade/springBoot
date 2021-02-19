package com.springmongo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.springmongo.exception.TodoCollectionException;
import com.springmongo.model.TodoDTO;
import com.springmongo.repository.TodoRepository;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	@Override
	public void createTodo(TodoDTO todo) throws TodoCollectionException,ConstraintViolationException {
		Optional<TodoDTO> todoOptional=todoRepository.findByTodo(todo.getTodo());
		if (todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
		} else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todo);
		}
	}

	@Override
	public List<TodoDTO> getAllTodos() {
		List<TodoDTO> todos=todoRepository.findAll();
		if (todos.isEmpty()) {
			return new ArrayList<TodoDTO>();
		} else {
			return todos;
		}
		
	}

	@Override
	public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
		Optional<TodoDTO> todo = todoRepository.findById(id);
		if (todo.isPresent()) {
			return todo.get();
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
	}

	@Override
	public void updateTodo(TodoDTO todoDTO) throws TodoCollectionException {
		Optional<TodoDTO> todo = todoRepository.findById(todoDTO.getId());
		if (todo.isPresent()) {
			todoDTO.setUpdatedAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todoDTO);
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(todoDTO.getId()));
		}
	}

	@Override
	public void deleteTodoById(String id) throws TodoCollectionException {
		Optional<TodoDTO> todo = todoRepository.findById(id);
		if (todo.isPresent()) {
			todoRepository.deleteById(id);
		} else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
	}

}

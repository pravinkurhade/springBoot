package com.springmongo.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springmongo.exception.TodoCollectionException;
import com.springmongo.model.TodoDTO;
import com.springmongo.repository.TodoRepository;
import com.springmongo.service.TodoService;

@RestController
public class TodoController {

	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private TodoService todoService;
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos(){
		List<TodoDTO> list= todoService.getAllTodos();
		return new ResponseEntity<List<TodoDTO>>(list, list.size()>0 ?HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo){
		try {
			todoService.createTodo(todo);
			return new ResponseEntity<TodoDTO>(todo,HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
		}catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable String id){
				try {
					return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
				} catch (TodoCollectionException e) {
					return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
				}	
	}
	
	@PutMapping("/todos")
	public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todoDTO){
		try {
			todoService.updateTodo(todoDTO); 
			return new ResponseEntity<>("Updated Todo with id "+ todoDTO.getId(), HttpStatus.OK);
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}	
	}
	
	@DeleteMapping("todos/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable String id){
		try {
			todoService.deleteTodoById(id);
			return new ResponseEntity<>("Successfully Deleted with id "+ id,HttpStatus.OK);
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
}

package com.edimar.pedido.controllers.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.edimar.pedido.exceptions.DataIntegrityViolationException;
import com.edimar.pedido.exceptions.GenericExcpetion;
import com.edimar.pedido.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandErrors> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandErrors error = new StandErrors(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<StandErrors> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request){
		StandErrors error = new StandErrors(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(GenericExcpetion.class)
	public ResponseEntity<StandErrors> genericExcpetion(GenericExcpetion e, HttpServletRequest request){
		StandErrors error = new StandErrors(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}

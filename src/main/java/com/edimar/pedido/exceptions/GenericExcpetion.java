package com.edimar.pedido.exceptions;

public class GenericExcpetion extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public GenericExcpetion(String msg) {
		super(msg);
	}
	
	public GenericExcpetion(String msg, Throwable cause) {
		super(msg, cause);
	}
}

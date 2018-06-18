package com.dmetal.ems.service;

/**
 * 自定义异常类
 * @author NiCo
 *
 */
public class ApplicationException extends RuntimeException {

	public ApplicationException() {
		
	}

	public ApplicationException(String message) {
		super(message);
	}
	
}

package com.edu.reactiveprogramming.model;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BadRequestError {

	private final HttpStatus status;
	private final String error;

	public BadRequestError(HttpStatus status, String error) {
		this.status = status;
		this.error = error;
	}
}

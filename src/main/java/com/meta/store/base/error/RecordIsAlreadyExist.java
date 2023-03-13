package com.meta.store.base.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RecordIsAlreadyExist extends RuntimeException  {

	private static final long serialVersionUID = 1L;
	
	public RecordIsAlreadyExist(String message) {
		super(message);
	}
}

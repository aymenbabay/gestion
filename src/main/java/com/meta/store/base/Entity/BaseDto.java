package com.meta.store.base.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseDto<ID> {

	private ID id;
	
	private String statusCode;
	
	private boolean isDeleted;
}

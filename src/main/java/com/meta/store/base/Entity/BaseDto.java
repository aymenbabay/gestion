package com.meta.store.base.Entity;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseDto<ID>  implements Serializable{

	private ID id;
	
	private String statusCode;
	
	private boolean isDeleted;
}

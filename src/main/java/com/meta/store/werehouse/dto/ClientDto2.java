package com.meta.store.werehouse.dto;

import java.io.Serializable;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.base.security.entity.AppUser;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDto2 extends BaseDto<Long> implements Serializable {


	@NotBlank(message = "Client Name Field Must Not Be Empty")
	private String name;

	@NotBlank(message = "Client Code Field Must Not Be Empty")
	@Column(unique = true)
	private String code;
	
	private String nature;
	
	private String phone;
	
	private String address;
	
	@Email
	private String email;


	private AppUser user;
}

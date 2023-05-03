package com.meta.store.base.security.controller;

import java.util.Set;

import com.meta.store.base.Entity.BaseEntity;
import com.meta.store.base.security.entity.Role;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest extends BaseEntity<Long> {

	private String phone;

	private String address;


	@NotBlank(message = "User Name Field Must Be Not Empty")
	@Column(unique = true)
	private String userName;
	
	private String email;

	@NotBlank(message = "Password Field Must Not Be Empty")
	private String password;
	
	//private Set<Role> roles;
}

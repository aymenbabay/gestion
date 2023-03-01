package com.meta.store.base.security.controller;

import java.util.Set;

import com.meta.store.base.security.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

	private String firstname;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	//private Set<Role> roles;
}

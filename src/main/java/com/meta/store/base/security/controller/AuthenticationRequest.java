package com.meta.store.base.security.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {


	@NotBlank(message = "User Name Field Must Be Not Empty")
	private String userName;

	@NotBlank(message = "Password Field Must Be Not Empty")
	private String password;
}

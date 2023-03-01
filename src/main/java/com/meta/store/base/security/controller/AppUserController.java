package com.meta.store.base.security.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.service.AppUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AppUserController {

	private final AppUserService appUserService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAll(){
		List<AppUser> users = appUserService.findAll();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('Admin')")
	public ResponseEntity<?> getById(@PathVariable Long id ) {
		return ResponseEntity.ok(appUserService.findById(id));
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
		return ResponseEntity.ok(appUserService.register(request));
	}
	
	@PostMapping("/authentication")
	public ResponseEntity<AuthenticationResponse> authentication(@RequestBody AuthenticationRequest request){
		return ResponseEntity.ok(appUserService.authenticate(request));
	}
}

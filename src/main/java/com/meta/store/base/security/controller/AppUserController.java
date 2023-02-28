package com.meta.store.base.security.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.service.AppUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AppUserController {

	private final AppUserService appUserService;
	
	@GetMapping("/all")
	public ResponseEntity<?> getAll(){
		List<AppUser> users = appUserService.findAll();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id ) {
		return ResponseEntity.ok(appUserService.findById(id));
	}
}

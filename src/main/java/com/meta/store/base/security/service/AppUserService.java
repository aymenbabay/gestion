package com.meta.store.base.security.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtService;
import com.meta.store.base.security.controller.AuthenticationRequest;
import com.meta.store.base.security.controller.AuthenticationResponse;
import com.meta.store.base.security.controller.RegisterRequest;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.entity.Role;
import com.meta.store.base.security.repository.AppUserRepository;
import com.meta.store.base.service.BaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService  {

	private final AppUserRepository appUserRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	private final RoleService roleService;
	
	public List<AppUser> findAll(){
		return appUserRepository.findAll();
	}
	
	public Optional<AppUser> findById(Long id) {
		return appUserRepository.findById(id);
	}
	
	public ResponseEntity<AppUser> insert(AppUser user) {
		return ResponseEntity.ok(appUserRepository.save(user));
	}
	public AppUser findByUserName(String name) {
		return appUserRepository.findByUserName(name).orElse(null);
	}
	
	

	public AuthenticationResponse register(RegisterRequest request) {
		Set<Role> role =  roleService.FindRoleByUserId((long) 1);
		AppUser user = AppUser.builder()
				.firstname(request.getFirstname())
				.userName(request.getUserName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.roles(role)
				.build();
		appUserRepository.save(user);
		var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
		var user = appUserRepository.findByUserName(request.getUserName()).orElseThrow();
var jwtToken = jwtService.generateToken(user);
		
		return AuthenticationResponse.builder().token(jwtToken).build();
	
	}
}

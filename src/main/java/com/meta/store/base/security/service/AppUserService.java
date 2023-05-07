package com.meta.store.base.security.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordIsAlreadyExist;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.config.JwtService;
import com.meta.store.base.security.controller.AuthenticationRequest;
import com.meta.store.base.security.controller.AuthenticationResponse;
import com.meta.store.base.security.controller.RegisterRequest;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.entity.Role;
import com.meta.store.base.security.repository.AppUserRepository;
import com.meta.store.werehouse.dto.AppUserDto;
import com.meta.store.werehouse.mapper.userMapper;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService  {


	private static final long EXPIRE_TOKEN_AFTER_MINUTES = 60;
	
	private final AppUserRepository appUserRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	private final RoleService roleService;
	
	private final userMapper userMapper;

	private final JwtAuthenticationFilter authenticationFilter;

	@Autowired
	private JavaMailSender mailSender;
	
	public List<AppUser> findAll(){
		return appUserRepository.findAll();
	}
	
	public Optional<AppUser> findById(Long id) {
		return appUserRepository.findById(id);
	}
	

	@CacheEvict(value = "user", key = "#root.methodName", allEntries = true)
	public ResponseEntity<AppUser> insert(AppUser user) {
		return ResponseEntity.ok(appUserRepository.save(user));
	}

	@Cacheable(value = "user", key = "#root.methodName + '_'+ #name")
	public AppUser findByUserName(String name) {
		return appUserRepository.findByUserName(name).orElse(null);
	}
	
	

	public AuthenticationResponse register(RegisterRequest request) {
		Set<Role> role = new HashSet<>();
		ResponseEntity<Role> role1 = roleService.getById((long)2);
		role.add(role1.getBody());
		Optional<AppUser> userr = appUserRepository.findByUserName(request.getUserName());
		if(userr.isPresent()) {
			throw new RecordIsAlreadyExist("This User Name Is Already Uses Please Take Another One");
		}
		AppUser user = AppUser.builder()
				.phone(request.getPhone())
				.userName(request.getUserName())
				.address(request.getAddress())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.roles( role)
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
	
	@Async
	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);
	}

	public Optional<AppUser> findUserByEmail(String email) {
		
		return appUserRepository.findByEmail(email);
	}

	public void save(AppUser userr) {
		appUserRepository.save(userr);
		
	}

	public boolean checkUserName(String username) {
		// TODO Auto-generated method stub
		return appUserRepository.existsByUserName(username);
	}

	public AppUserDto getByUserName(String username) {
		Optional<AppUser> user = appUserRepository.findByUserName(username);
		AppUserDto appUserDto = userMapper.mapToDto(user.get());
		return appUserDto;
	}

	public AuthenticationResponse refreshToken(String token) {
		AppUser user = findByUserName(authenticationFilter.userName);
		 // validate the input token
	    if (!jwtService.isTokenValid(token,user)) {
	       // throw new InvalidTokenException("Invalid refresh token");
	    }
	    
	    
	    // generate a new authentication token
	    String accessToken = jwtService.generateToken(user);
	    
	    // create a new authentication response
	    AuthenticationResponse response = new AuthenticationResponse(accessToken);
	    
	    // return the response
	    return response;
	}
}

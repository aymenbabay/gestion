package com.meta.store.base.AppConfig;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.entity.Role;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.security.service.RoleService;

import lombok.RequiredArgsConstructor;

@Component
public class StartUpApp implements CommandLineRunner {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public ResponseEntity<?> insertRole(String rol){
		Role role = new Role(rol);
		return roleService.insert(role);
	}
	
	public ResponseEntity<?> insertUser(Set<Role> roles){
		
		AppUser user = new AppUser("aymen babay","user","aymen1",passwordEncoder.encode("password"),roles);
		
		return appUserService.insert(user);
	}
	@Override
	public void run(String... args) throws Exception {

		if(roleService.findAll().isEmpty()) {
		insertRole("admin");
		insertRole("user");
		Set<Role> adminRole = new HashSet<>();
		adminRole.add(roleService.findByName("admin"));
		adminRole.add(roleService.findByName("user"));
		insertUser(adminRole);
		
		}
	}

}

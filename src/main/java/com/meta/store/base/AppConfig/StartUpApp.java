package com.meta.store.base.AppConfig;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.entity.Role;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.security.service.RoleService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StartUpApp implements CommandLineRunner {

	private final RoleService roleService;
	
	private final AppUserService appUserService;
	
	public ResponseEntity<?> insertRole(String rol){
		Role role = new Role();
		role.setName(rol);
		return roleService.insert(role);
	}
	
	public ResponseEntity<?> insertUser(Set<Role> roles){
		AppUser user = new AppUser();
		user.setFullName("aymen babay");
		user.setUserName("aymen1");
		user.setPassword("password");
		user.setRoles(roles);
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

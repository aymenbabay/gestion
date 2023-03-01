package com.meta.store.base.security.service;

import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.security.entity.Role;
import com.meta.store.base.security.repository.RoleRepository;
import com.meta.store.base.service.BaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleService extends BaseService<Role, Long> {

	private final RoleRepository roleRepository;
	
	public List<Role> findAll() {
		return super.getAll();
	}
	
	public ResponseEntity<Role> findById(Long id) {
		return super.getById(id);
	}
	
	public Role findByName(String name) {
		return roleRepository.findByName(name).orElse(null);
	}
	
	public ResponseEntity<Role> insert(Role role){
		return super.insert(role);
	}
	
	public Set<Role> FindRoleByUserId(Long id){
		return roleRepository.findRolesByUserId(id);
	}
}

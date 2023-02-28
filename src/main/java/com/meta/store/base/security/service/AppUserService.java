package com.meta.store.base.security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.repository.AppUserRepository;
import com.meta.store.base.service.BaseService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AppUserService extends BaseService<AppUser, Long> {

	private final AppUserRepository appUserRepository;
	
	public List<AppUser> findAll(){
		return super.getAll();
	}
	
	public AppUser findById(Long id) {
		return super.getById(id).orElseThrow();
	}
	
	public AppUser findByUserName(String name) {
		return appUserRepository.findByUserName(name).orElse(null);
	}
	
	public ResponseEntity<AppUser> insert(AppUser user) {
		return super.insert(user);
	}
}

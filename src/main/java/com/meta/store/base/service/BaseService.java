package com.meta.store.base.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.meta.store.base.Entity.BaseEntity;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.repository.BaseRepository;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseService<T extends BaseEntity<ID>,ID extends Number> {

	@Autowired
	private BaseRepository<T, ID> baseRepository;
	
	public List<T> getAll(){
		return  baseRepository.findAll();
	}
	
	public Optional<T> getById(ID id) {
		Optional<T> entity =  baseRepository.findById(id);
		if(entity.isPresent()) {
			return entity;
		}else {
			throw new RecordNotFoundException("this record with id: "+id+" not found");
		}
	}
	
	public ResponseEntity<T> insert(T article) {

		baseRepository.save(article);
		return	new ResponseEntity<T>(HttpStatus.OK);
	}
	
	public ResponseEntity<T> insertAll(List<T> articles){
		baseRepository.saveAll(articles);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public void deleteById(ID id) {
				baseRepository.deleteById(id);
	}
	
	
}

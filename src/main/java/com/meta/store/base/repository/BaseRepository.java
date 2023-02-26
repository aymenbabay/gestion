package com.meta.store.base.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import jakarta.transaction.Transactional;

@NoRepositoryBean
public interface BaseRepository<T,ID> extends JpaRepository<T, ID> {

//	@Modifying
//	@Transactional
//	@Query("SELECT * FROM #{#entityName} t ")
//	List<T> findAll(); 
}

package com.meta.store.base.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.base.Entity.BaseEntity;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;

import jakarta.transaction.Transactional;

@NoRepositoryBean
public interface BaseRepository<T,ID> extends JpaRepository<T, ID> {

	BaseDto<Long> findCompanyById(Long id);


	//@Query("SELECT a.client_id from Client a , company_client r WHERE r.client_id = a.id and r.company_id = :companyId ")
	//List<T> findAllByCompanyId(ID companyId);

   
    
//	@Modifying
//	@Transactional
//	@Query("SELECT * FROM #{#entityName} t ")
//	List<T> findAll(); 
}

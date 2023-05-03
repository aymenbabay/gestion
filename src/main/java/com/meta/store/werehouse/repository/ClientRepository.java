package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.dto.ClientDto;
import com.meta.store.werehouse.entity.Client;

import jakarta.validation.Valid;


public interface ClientRepository extends BaseRepository<Client, Long> {

	List<Client> findByName(String name);

	Optional<Client> findByCode(String code);

	@Query("SELECT c FROM Client c JOIN c.companies co WHERE co.id = :companyId AND c.code = :code")
	Optional<Client> findByCodeAndCompanyId(String code, Long companyId);
	
	@Query( "SELECT c FROM Client c JOIN c.companies co WHERE co.id = :companyId")
	List<Client> getAllByCompanyId( Long companyId);
	

	@Query("SELECT c FROM Client c JOIN c.companies co WHERE co.id = :companyId AND c.name = :name")
	List<Client> findByNameAndCompanyId(@Valid String name, Long companyId);


	Optional<Client> findByUserId(Long userId);
	
	
	
//	@Modifying
//	@Query("delete FROM Client c JOIN c.companies co WHERE c.id = :id")
//	void deleteByClientId(Long id);
	
	
	
	
/*
	Optional<Client> findByNameAndCompanyId(String name, Long id);

	List<Client> findAllByCompanyId(Long companyId);

	Optional<Client> findByCodeAndCompanyId(@Valid String code, Long companyId);

	Optional<Client> findByIdAndCompanyId(Long id, Long companyId);

	Integer deleteByIdAndCompanyId(Long id, Long companyId);
*/


	
	//Set<Client> getByCompanyId(@Param("companyId") Long companyId);




}

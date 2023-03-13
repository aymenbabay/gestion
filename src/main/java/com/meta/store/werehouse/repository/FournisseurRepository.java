package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Fournisseur;
import com.meta.store.werehouse.entity.Fournisseur;

import jakarta.validation.Valid;

public interface FournisseurRepository  extends BaseRepository<Fournisseur,Long>{

	List<Fournisseur> findByName(String name);

	Optional<Fournisseur> findByCode(String code);

	@Query("SELECT c FROM Fournisseur c JOIN c.companies co WHERE co.id = :companyId AND c.code = :code")
	Optional<Fournisseur> findByCodeAndCompanyId(String code, Long companyId);
	
	@Query( "SELECT c FROM Fournisseur c JOIN c.companies co WHERE co.id = :companyId")
	List<Fournisseur> getAllByCompanyId( Long companyId);
	

	@Query("SELECT c FROM Fournisseur c JOIN c.companies co WHERE co.id = :companyId AND c.name = :name")
	List<Fournisseur> findByNameAndCompanyId(@Valid String name, Long companyId);


	Optional<Fournisseur> findByUserId(Long userId);
}

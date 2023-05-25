package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Company;
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
	List<Fournisseur> findByNameAndCompanyId(String name, Long companyId);


	Optional<Fournisseur> findByUserId(Long userId);

	@Query("SELECT f FROM Fournisseur f WHERE f.user.id IS NOT NULL")
	List<Fournisseur> findAllHasUserId();

	@Query("SELECT f FROM Fournisseur f JOIN f.companies co WHERE co.id = :companyId AND f.user.id IS NULL")
	List<Fournisseur> findAllMyVirtual(Long companyId);

	@Query("SELECT COUNT(f) > 0 FROM Fournisseur f JOIN f.companies c WHERE f.id = :fournisseurId AND c.id = :companyId")
	boolean existsMyProvider(Long companyId, Long fournisseurId);

}

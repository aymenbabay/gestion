package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.SousCategory;

public interface SousCategoryRepository extends BaseRepository<SousCategory,Long> {

	Optional<SousCategory> findByCodeAndCompanyId(String code, Long id);

	@Query("SELECT a FROM SousCategory a WHERE a.company.id = :companyId")
	List<SousCategory> findAllByCompanyId(@Param("companyId") Long companyId);

	Optional<SousCategory> findByIdAndCompanyId(Long id, Long id2);

	Optional<SousCategory> findByLibelleAndCompanyId(String libelle, Long companyId);

}

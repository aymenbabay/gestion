package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Category;

public interface CategoryRepository  extends BaseRepository<Category,Long>{

	Optional<Category> findByLibelleAndCompanyId(String libelle, Long companyId);

	@Query("SELECT a FROM Category a WHERE a.company.id = :companyId")
	List<Category> findAllByCompanyId(@Param("companyId") Long companyId);

	Optional<Category> findByIdAndCompanyId(Long id , Long companyId);

}

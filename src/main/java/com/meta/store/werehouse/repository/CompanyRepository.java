package com.meta.store.werehouse.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Company;

public interface CompanyRepository extends BaseRepository<Company, Long> {


	//@Query(value = "select art from Company art join art.department dept where dept.id = :deptId")
	//Company findCompanyByUserId(Long id);


	boolean existsByName(String name);
	
	boolean existsByUserId(Long id);
	
	void deleteByIdAndUserId(Long id, Long userId );

	Company findByUserId(Long userId);
	
//	  @Query("SELECT COUNT(c) > 0 FROM Company c JOIN c.providers f WHERE c.id = :id AND f.id = :fournisseurId")
//	    boolean existsByIdAndFournisseurId(@Param("id") Long id, @Param("fournisseurId") Long fournisseurId);

}

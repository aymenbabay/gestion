package com.meta.store.werehouse.repository;

import org.springframework.data.jpa.repository.Query;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Company;

public interface CompanyRepository extends BaseRepository<Company, Long> {


	//@Query(value = "select art from Company art join art.department dept where dept.id = :deptId")
	//Company findCompanyByUserId(Long id);

	long countByCompanyName(String companyName);

	long countByUserId(Long id);
	
	void deleteByIdAndUserId(Long id, Long userId );

	Company findByUserId(Long userId);
}

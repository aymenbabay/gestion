package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Invoice;

public interface InvoiceRepository extends BaseRepository<Invoice, Long> {

	Optional<Invoice> findByCodeAndCompanyId(Long code, Long companyId);

	@Query("SELECT a FROM Invoice a WHERE a.company.id = :companyId")
	List<Invoice> findAllByCompanyId(@Param("companyId")Long companyId);

	Optional<Invoice> findByIdAndCompanyId(Long id, Long companyId);

}

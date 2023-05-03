package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.entity.Invoice;

public interface InvoiceRepository extends BaseRepository<Invoice, Long> {

	Optional<Invoice> findByCodeAndCompanyId(Long code, Long companyId);

	@Query("SELECT a.code FROM Invoice a WHERE a.company.id = :companyId")
	List<Long> findAllByCompany(@Param("companyId")Long companyId);

	Optional<Invoice> findByIdAndCompanyId(Long id, Long companyId);

	@Query("SELECT max(code) FROM Invoice i WHERE i.company.id = :companyId ")
	Long max(Long companyId);
	
	@Query("SELECT i FROM Invoice i WHERE i.company.id = :companyId AND i.code = (SELECT max(i2.code) FROM Invoice i2 WHERE i2.company.id = :companyId)")
	Invoice lastInvoice(Long companyId);


	Optional<Invoice> findByCodeAndClientId(Long code, Long clientId);

	@Query("SELECT a.code FROM Invoice a WHERE a.client.id = :clientId")
	List<Long> findByClientId(Long clientId);

	List<Invoice> findAllByCompanyId(Long companyId);

	List<Invoice> findAllByClientId(Long clientId);

}

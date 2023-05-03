package com.meta.store.werehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.CommandLine;

public interface CommandLineRepository extends BaseRepository<CommandLine,Long>{

	List<CommandLine> findByInvoiceCode(Long code);

	List<CommandLine> findByInvoiceId( Long invoiceId);
	
	void deleteByInvoiceId(Long invoiceId);

}

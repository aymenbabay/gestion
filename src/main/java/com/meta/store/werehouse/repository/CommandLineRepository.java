package com.meta.store.werehouse.repository;

import java.util.List;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.CommandLine;

public interface CommandLineRepository extends BaseRepository<CommandLine,Long>{

	List<CommandLine> findByInvoiceCode(Long code);
}

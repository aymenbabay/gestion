package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Worker;

public interface WorkerRepository extends BaseRepository<Worker,Long> {

	Optional<Worker> findByNameAndCompanyId(String name, Long id);

	@Query("SELECT a FROM Worker a WHERE a.company.id = :companyId")
	List<Worker> findAllByCompanyId(@Param("companyId") Long companyId);

	Optional<Worker> findByIdAndCompanyId(Long id, Long companyId);

}

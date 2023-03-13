package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Shipment;

public interface ShipmentRepository extends BaseRepository<Shipment,Long> {

	Optional<Shipment> findByCodeAndCompanyId(String code, Long id);

	@Query("SELECT a FROM Shipment a WHERE a.company.id = :companyId")
	List<Shipment> findAllByCompanyId(@Param("companyId") Long companyId);

	Optional<Shipment> findByIdAndCompanyId(Long id, Long id2);

}

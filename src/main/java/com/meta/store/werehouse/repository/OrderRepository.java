package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Order;

public interface OrderRepository  extends BaseRepository<Order,Long>{

	Optional<Order> findByCodeAndCompanyId(Long code, Long id);

	@Query("SELECT a FROM Order a WHERE a.company.id = :companyId")
	List<Order> findAllByCompanyId(@Param("companyId") Long companyId);

	Optional<Order> findByIdAndCompanyId(Long id, Long id2);

}

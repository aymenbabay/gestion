package com.meta.store.werehouse.repository;

import java.util.List;
import java.util.Optional;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Inventory;

public interface InventoryRepository extends BaseRepository<Inventory,Long> {

	List<Inventory> findByCompanyId(Long companyId);

	Optional<Inventory> findByCompanyIdAndArticleCode(Long companyId, String articleCode);

	void deleteByCompanyIdAndArticleCode(Long companyId, String articleCode);

}

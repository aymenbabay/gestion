package com.meta.store.werehouse.repository;

import java.util.List;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.Inventory;

public interface InventoryRepository extends BaseRepository<Inventory,Long> {

	Inventory findByCompanyId(Long companyId);

}

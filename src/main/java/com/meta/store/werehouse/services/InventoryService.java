package com.meta.store.werehouse.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.CategoryDto;
import com.meta.store.werehouse.dto.InventoryDto;
import com.meta.store.werehouse.entity.Category;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Inventory;
import com.meta.store.werehouse.mapper.CategoryMapper;
import com.meta.store.werehouse.mapper.InventoryMapper;
import com.meta.store.werehouse.repository.CategoryRepository;
import com.meta.store.werehouse.repository.InventoryRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService extends BaseService<Inventory, Long> {

	private final InventoryMapper inventoryMapper;
	
	private final InventoryRepository inventoryRepository;
	

	public ResponseEntity<InventoryDto> getInventoryByCompanyId(Long companyId) {

		Inventory inventory = inventoryRepository.findByCompanyId(companyId);
		if(inventory != null) {
		InventoryDto inventoryDto = inventoryMapper.mapToDto(inventory);
		return ResponseEntity.ok(inventoryDto);
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need");
	}

}

package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.InventoryDto;
import com.meta.store.werehouse.entity.Inventory;

@Mapper
public interface InventoryMapper {
	
 	InventoryDto mapToDto (Inventory entity);
	
 	Inventory mapToEntity (InventoryDto dto);
}

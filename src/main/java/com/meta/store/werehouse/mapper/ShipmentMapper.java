package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.ShipmentDto;
import com.meta.store.werehouse.entity.Shipment;

@Mapper
public interface ShipmentMapper {


 	ShipmentDto mapToDto (Shipment entity);
	
 	Shipment mapToEntity (ShipmentDto dto);
}

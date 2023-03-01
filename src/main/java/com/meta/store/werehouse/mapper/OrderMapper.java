package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.OrderDto;
import com.meta.store.werehouse.entity.Order;

@Mapper
public interface OrderMapper {


 	OrderDto mapToDto (Order entity);
	
 	Order mapToEntity (OrderDto dto);
}

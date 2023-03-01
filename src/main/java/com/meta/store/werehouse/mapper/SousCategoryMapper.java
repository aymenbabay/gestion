package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.SousCategoryDto;
import com.meta.store.werehouse.entity.SousCategory;

@Mapper
public interface SousCategoryMapper {


 	SousCategoryDto mapToDto (SousCategory entity);
	
 	SousCategory mapToEntity (SousCategoryDto dto);
}

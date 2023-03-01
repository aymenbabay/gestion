package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.CategoryDto;
import com.meta.store.werehouse.entity.Category;

@Mapper
public interface CategoryMapper {

    CategoryDto mapToDto (Category entity);
	
    Category mapToEntity (CategoryDto dto);
}

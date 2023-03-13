package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.CompanyDto;
import com.meta.store.werehouse.entity.Company;

@Mapper
public interface CompanyMapper {

	CompanyDto mapToDto (Company entity);
	
	Company mapToEntity (CompanyDto dto);
	
	

}

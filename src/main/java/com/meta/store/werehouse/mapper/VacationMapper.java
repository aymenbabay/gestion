package com.meta.store.werehouse.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.VacationDto;
import com.meta.store.werehouse.entity.Vacation;

@Mapper
public interface VacationMapper {

	VacationDto mapToDto (Vacation entity);
	
	@InheritInverseConfiguration
	Vacation mapToEntity (VacationDto dto);
}

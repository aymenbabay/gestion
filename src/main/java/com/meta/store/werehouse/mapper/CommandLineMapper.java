package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.CommandLineDto;
import com.meta.store.werehouse.entity.CommandLine;

@Mapper
public interface CommandLineMapper {

	CommandLineDto mapToDto (CommandLine entity);
	
	CommandLine mapToEntity (CommandLineDto dto);
}

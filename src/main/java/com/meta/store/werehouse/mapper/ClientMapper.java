package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.ClientDto;
import com.meta.store.werehouse.entity.Client;

@Mapper
public interface ClientMapper {

	ClientDto mapToDto (Client entity);
	
	Client mapToEntity (ClientDto dto);
}

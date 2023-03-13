package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.ClientDto2;
import com.meta.store.werehouse.entity.Client;
@Mapper
public interface ClientMapper2 {

	ClientDto2 mapToDto (Client entity);
	Client mapToEntity (ClientDto2 dto);
	
}

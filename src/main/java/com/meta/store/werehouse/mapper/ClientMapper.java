package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.ClientDto;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;

@Mapper
public interface ClientMapper {

	ClientDto mapToDto (Client entity);
	
	Client mapToEntity (ClientDto dto);
	
	Client mapCompanyToClient(Company company);
}

package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.FournisseurDto;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Fournisseur;

@Mapper
public interface FournisseurMapper {

	 	FournisseurDto mapToDto (Fournisseur entity);
		
	 	Fournisseur mapToEntity (FournisseurDto dto);
	 	
	 	Fournisseur mapCompanyToFournisseur (Company company);
}

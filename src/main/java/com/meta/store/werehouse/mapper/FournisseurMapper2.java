package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.FournisseurDto2;
import com.meta.store.werehouse.entity.Fournisseur;

@Mapper
public interface FournisseurMapper2 {

	FournisseurDto2 mapToDto(Fournisseur entity);
	
	Fournisseur mapToEntity(FournisseurDto2 dto);
	
}

package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.CompanyArticleDto;
import com.meta.store.werehouse.entity.CompanyArticle;

@Mapper
public interface CompanyArticleMapper {

	CompanyArticle mapToEntity(CompanyArticleDto dto);
	
	CompanyArticleDto mapToDto(CompanyArticle entity);
}

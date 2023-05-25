package com.meta.store.werehouse.mapper;


import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.entity.Article;

@Mapper
public interface ArticleMapper {
	
	//ArticleMapper MAPPER = Mappers.getMapper(ArticleMapper.class);

	//@Mapping(source = "fournisseur", target = "provider")
	ArticleDto mapToDto (Article entity);
	
	@InheritInverseConfiguration
	Article mapToEntity (ArticleDto dto);

}

package com.meta.store.werehouse.mapper;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.entity.Article;

@Mapper
public interface ArticleMapper {
	
	ArticleMapper MAPPER = Mappers.getMapper(ArticleMapper.class);

	ArticleDto mapToDto (Article entity);
	
	Article mapToEntity (ArticleDto dto);

}

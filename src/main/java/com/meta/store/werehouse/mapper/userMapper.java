package com.meta.store.werehouse.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.meta.store.base.security.entity.AppUser;
import com.meta.store.werehouse.dto.AppUserDto;

@Mapper
public interface userMapper {

	@Mapping(source = "username", target = "name")
	AppUserDto mapToDto (AppUser entity);
	
	@InheritInverseConfiguration
	AppUser mapToEntity (AppUserDto dto);
}

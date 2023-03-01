package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.entity.Worker;

@Mapper
public interface WorkerMapper {


 	WorkerDto mapToDto (Worker entity);
	
 	Worker mapToEntity (WorkerDto dto);
}

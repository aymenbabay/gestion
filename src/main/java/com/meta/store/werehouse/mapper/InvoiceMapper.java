package com.meta.store.werehouse.mapper;

import org.mapstruct.Mapper;

import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.entity.Invoice;

@Mapper
public interface InvoiceMapper {


 	InvoiceDto mapToDto (Invoice entity);
	
 	Invoice mapToEntity (InvoiceDto dto);
}

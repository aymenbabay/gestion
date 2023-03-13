package com.meta.store.werehouse.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordIsAlreadyExist;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.dto.InventoryDto;
import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Inventory;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.mapper.InventoryMapper;
import com.meta.store.werehouse.mapper.InvoiceMapper;
import com.meta.store.werehouse.repository.InventoryRepository;
import com.meta.store.werehouse.repository.InvoiceRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService extends BaseService<Invoice, Long>  {


	private final InvoiceMapper invoiceMapper;
	
	private final InvoiceRepository invoiceRepository;
	
	public ResponseEntity<InvoiceDto> upDateInvoice( InvoiceDto invoiceDto, Company company) {
		if(company == null) {
			throw new RecordNotFoundException("You Have No A Company Please Create One If You Need");
		}
		Optional<Invoice> invoice = invoiceRepository.findByIdAndCompanyId(invoiceDto.getId(),company.getId());
		if(invoice.isEmpty()) {
			throw new RecordNotFoundException("Invoice Not Found");
		}
			Invoice categ = invoiceMapper.mapToEntity(invoiceDto);
			categ.setCompany(company);
			invoiceRepository.save(categ);
			return ResponseEntity.ok(invoiceDto);
	}

	

	public ResponseEntity<List<InvoiceDto>> getAllByCompanyId(Long companyId) {
		List<Invoice> invoices = invoiceRepository.findAllByCompanyId(companyId);
		if(invoices.isEmpty()) {
			throw new RecordNotFoundException("there is no invoice");
		}
		List<InvoiceDto> invoicesDto = new ArrayList<>();
		for(Invoice i : invoices) {
			InvoiceDto invoiceDto = invoiceMapper.mapToDto(i);
			invoicesDto.add(invoiceDto);
		}
		
		return ResponseEntity.ok(invoicesDto);
		 
	}

	public ResponseEntity<InvoiceDto> getByCodeAndCompanyId(Long code, Long companyId) {

		if(companyId == null) {
			throw new RecordNotFoundException("You Have No Company Please Create One :) ");
		}
		Optional<Invoice> invoice = invoiceRepository.findByCodeAndCompanyId(code,companyId);
		if(invoice.isEmpty()) {
			 throw new RecordNotFoundException("There Is No Invoice With Libelle : "+code);
		}
		InvoiceDto dto = invoiceMapper.mapToDto(invoice.get());
		return ResponseEntity.ok(dto);
		
	}
	
	public Optional<Invoice> getByIdAndCompanyId(Long id , Long companyId) {
		return invoiceRepository.findByIdAndCompanyId(id, companyId);
	}



	public ResponseEntity<InvoiceDto> insertInvoice(@Valid InvoiceDto invoiceDto, Company company) {
		if(company == null) {
			throw new RecordNotFoundException("You Have No Company Please Create One If You need");
		}
		Optional<Invoice> invoice1 = invoiceRepository.findByCodeAndCompanyId(invoiceDto.getCode(),company.getId());
		if(invoice1.isPresent())  {
			throw new RecordIsAlreadyExist("Invoice Code Is Already uses");
		}
		Invoice invoice = invoiceMapper.mapToEntity(invoiceDto);
		invoice.setCompany(company);
		super.insert(invoice);
		return new ResponseEntity<InvoiceDto>(HttpStatus.ACCEPTED);
		
	}



	public void deleteInvoiceById(Long id, Company company) {
		
		if(company == null) {
			throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
		}
			Optional<Invoice> invoice = invoiceRepository.findByIdAndCompanyId(id,company.getId());
			if(invoice.isEmpty()) {
				throw new RecordNotFoundException("This Invoice Does Not Exist");
			}
		 super.deleteById(id,company.getId());
			
	}
	

	
	
}

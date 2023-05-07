package com.meta.store.werehouse.services;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.CommandLine;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.InvoiceMapper;
import com.meta.store.werehouse.repository.CommandLineRepository;
import com.meta.store.werehouse.repository.InvoiceRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class InvoiceService extends BaseService<Invoice, Long>  {


	private final InvoiceMapper invoiceMapper;
	
	private final InvoiceRepository invoiceRepository;
	
	private final CommandLineRepository commandLineRepository;
	
	private final ClientService clientService;
	
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

	

	public ResponseEntity<List<Long>> getAllByCompanyId(Long companyId, Long userId) {
		List<Long> invoices = invoiceRepository.findAllByCompany(companyId);
		Optional<Client> client = clientService.getByUserId(userId);
		if(client.isPresent()) {
		List<Long> invoices1 = invoiceRepository.findByClientId(client.get().getId());
		invoices.addAll(invoices1);
		}
		Set<Long> myset = new HashSet<Long>(invoices);
		List<Long> unique = new ArrayList<Long>(myset);
		if(invoices.isEmpty()) {
			throw new RecordNotFoundException("there is no invoice");
		}
		 return ResponseEntity.ok(unique);
	}

	public ResponseEntity<List<InvoiceDto>> getByCodeAndCompanyId(Long code, Long companyId, Long userId) {

		if(companyId == null) {
			throw new RecordNotFoundException("You Have No Company Please Create One :) ");
		}
		List<InvoiceDto> invoices = new ArrayList<>();
		Optional<Invoice> invoice = invoiceRepository.findByCodeAndCompanyId(code,companyId);
		Optional<Client> client = clientService.getByUserId(userId);
		if(invoice.isEmpty()) {
			if(client.isPresent()) {
			Optional<Invoice> invoice1 = invoiceRepository.findByCodeAndClientId(code,client.get().getId());
			if(invoice1.isEmpty()) {
			 throw new RecordNotFoundException("There Is No Invoice With Code : "+code);
			}

			InvoiceDto dto1 = invoiceMapper.mapToDto(invoice1.get());
			invoices.add(dto1);
			}else 
				 throw new RecordNotFoundException("There Is No Invoice With Code : "+code);
		}
		InvoiceDto dto1 = null;
		if(client.isPresent()) {
		Optional<Invoice> invoice1 = invoiceRepository.findByCodeAndClientId(code,client.get().getId());
		if(invoice1.isPresent()) {
			dto1 = invoiceMapper.mapToDto(invoice1.get());
			invoices.add(dto1);
		}
		}
		InvoiceDto dto = invoiceMapper.mapToDto(invoice.get());
		if(dto1 != dto) {
		invoices.add(dto);
		}
		return ResponseEntity.ok(invoices);
		
	}
	
	public Optional<Invoice> getByIdAndCompanyId(Long id , Long companyId) {
		return invoiceRepository.findByIdAndCompanyId(id, companyId);
	}



	public ResponseEntity<InvoiceDto> insertInvoice(Client client, Company company) {
		if(company == null) {
			throw new RecordNotFoundException("You Have No Company Please Create One If You need");
		}
		Invoice invoice = new Invoice();
		Long max = this.max(company.getId());
		if(max != null) {
			
			invoice.setCode(max+1);
		
		}
		if(max == null) {
			invoice.setCode((long) 2053);
		}
		invoice.setClient(client);
		invoice.setCompany(company);		
		invoiceRepository.save(invoice);
			return null;
		
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
			commandLineRepository.deleteByInvoiceId(id);
	}



	public Long max(Long id) {
		return invoiceRepository.max(id);
	}



	public List<InvoiceDto> getMyByCompany(Long companyId, Long userId) {
		if(companyId == null || userId== null) {
			throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
		}
		
		List<Invoice> invoices = invoiceRepository.findAllByCompanyId(companyId);
		List<InvoiceDto> dtos = new ArrayList<>();
		for(Invoice i : invoices) {
			InvoiceDto dto = invoiceMapper.mapToDto(i);
			dtos.add(dto);
		}
		return dtos;
	}


	public InvoiceDto getLastInvoice(Long companyId) {
		Invoice invoice = invoiceRepository.lastInvoice(companyId);
		InvoiceDto invoiceDto = invoiceMapper.mapToDto(invoice);
		return invoiceDto;
	}



	public ResponseEntity<InputStreamResource> export(Company company, List<CommandLine> commandLines) {
		InvoiceDto invoice = getLastInvoice(company.getId());
		ByteArrayInputStream bais = exportInvoicePdf.invoicePdf(commandLines,invoice,company);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=invoice.pdf");
		 ResponseEntity<InputStreamResource> response = ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(bais));

		   // logger.info("Response: " + response.toString());

		    return response;
	}



	public List<InvoiceDto> getMyAsClient(Long id) {
		Optional<Client> clientId = clientService.getByUserId(id);
		if(clientId.isEmpty()) {
			throw new RecordNotFoundException("you're not a client");
		}
		List<Invoice> invoices = invoiceRepository.findAllByClientId(clientId.get().getId());
		List<InvoiceDto> invoicesDto = new ArrayList<>();
		for(Invoice i : invoices) {
			InvoiceDto invoiceDto = invoiceMapper.mapToDto(i);
			invoicesDto.add(invoiceDto);
		}
		return invoicesDto;
	}



	
	

	
	
}

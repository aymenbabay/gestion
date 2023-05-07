package com.meta.store.werehouse.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.werehouse.dto.ClientDto;
import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.InvoiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Validated
@RestController
@RequestMapping("/werehouse/invoice")
@RequiredArgsConstructor
public class InvoiceController {

	
	private final InvoiceService invoiceService;
			
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
		
	private final CompanyService companyService;
	
	
	@GetMapping("/code/{code}")
	public ResponseEntity<List<InvoiceDto>> getInvoiceByCode(@PathVariable Long code){
		Company company = getCompany();
		return invoiceService.getByCodeAndCompanyId(code, company.getId(), company.getUser().getId());
				
	}
	
	//cause it's use in client service
	@PostMapping("/add")
	public ResponseEntity<InvoiceDto> insertInvoice(@RequestBody @Valid Client client){
		Company company = getCompany();
		return invoiceService.insertInvoice(client,company);
	}
	
	
	@GetMapping("/getbycompany")
	public ResponseEntity<List<Long>> getInvoiceByCompany(){
		Company company = getCompany();
		return invoiceService.getAllByCompanyId(company.getId(),company.getUser().getId());
		
	}

	@GetMapping("/getMyByCompany")
	public List<InvoiceDto> getMyByCompany(){
		Company company = getCompany();
		return invoiceService.getMyByCompany(company.getId(),company.getUser().getId());
	}
	
	@GetMapping("/getAsClient")
	public List<InvoiceDto> getMyAsClient(){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		return invoiceService.getMyAsClient(userId);
	}
	@GetMapping("/getlastinvoice")
	public InvoiceDto getLastInvoice() {
		Company company = getCompany();
		return invoiceService.getLastInvoice(company.getId());
		
	}
	
	@PutMapping("/update")
	public ResponseEntity<InvoiceDto> upDateInvoice(@RequestBody @Valid InvoiceDto invoiceDto){
		Company company = getCompany();
		return invoiceService.upDateInvoice(invoiceDto, company);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteInvoiceById(@PathVariable Long id){
		Company company = getCompany();
		 invoiceService.deleteInvoiceById(id,company);
	}
	
	private Company getCompany() {

		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("you do not have a company");
		}
		return company;
	}
	
}

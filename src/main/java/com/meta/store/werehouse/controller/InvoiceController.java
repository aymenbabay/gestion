package com.meta.store.werehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.services.InvoiceService;

import lombok.RequiredArgsConstructor;


@Validated
@RestController
@RequestMapping("/werehouse/invoice")
@RequiredArgsConstructor
public class InvoiceController {


	
	private final InvoiceService invoiceService;
	
	@PostMapping("/add")
	public ResponseEntity<Invoice> insert(@RequestBody Invoice invoice){
		invoiceService.insert(invoice);
		return new ResponseEntity<Invoice>(HttpStatus.ACCEPTED);
	}
	 
	
}

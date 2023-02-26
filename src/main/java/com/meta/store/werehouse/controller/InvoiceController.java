package com.meta.store.werehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.entity.Invoice;

@Validated
@RestController
@RequestMapping("/werehouse/invoice")
public class InvoiceController {


	@Autowired
	private BaseService<Invoice, Long> baseService;
	
	
}

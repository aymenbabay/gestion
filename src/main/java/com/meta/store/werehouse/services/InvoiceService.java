package com.meta.store.werehouse.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.repository.InvoiceRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class InvoiceService extends BaseService<Invoice, Long>  {

	@Autowired
	private InvoiceRepository invoiceRepository;
	
	@Override
	public ResponseEntity<Invoice> insert(Invoice invoice) {
		return super.insert(invoice);
	}
	
	
}

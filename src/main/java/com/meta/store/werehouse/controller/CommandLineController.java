package com.meta.store.werehouse.controller;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.werehouse.dto.CommandLineDto;
import com.meta.store.werehouse.dto.InvoiceDto;
import com.meta.store.werehouse.entity.CommandLine;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.services.CommandLineService;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.InvoiceService;
import com.meta.store.werehouse.services.exportInvoicePdf;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/werehouse/commandline")
@RequiredArgsConstructor
@Validated
public class CommandLineController {

	private final CommandLineService commandLineService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
		
	private final CompanyService companyService;
	
	private final InvoiceService invoiceService;
	
	
	private static final Logger logger = Logger.getLogger(exportInvoicePdf.class.getName());

	
	@PostMapping("/{type}/{code}/{id}")
	public ResponseEntity<InputStreamResource> addCommandLine(@RequestBody @Valid List<CommandLineDto> commandLinesDto,
			@PathVariable Long code, @PathVariable String type, @PathVariable Long id) {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		commandLineService.insertLine(commandLinesDto, company);
		if (type.equals("pdf-save-client") ) {	
			List<CommandLine> commandLines = commandLineService.getCommandLineByInvoiceId(code);
			return invoiceService.export(company,commandLines);
			
		}
		return null;
		
	}
	
	


}

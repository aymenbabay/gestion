package com.meta.store.werehouse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
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

import com.meta.store.base.error.RecordIsAlreadyExist;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.InventoryDto;
import com.meta.store.werehouse.dto.InventoryDto;
import com.meta.store.werehouse.dto.InventoryDto;
import com.meta.store.werehouse.entity.Inventory;
import com.meta.store.werehouse.entity.Inventory;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Inventory;
import com.meta.store.werehouse.mapper.InventoryMapper;
import com.meta.store.werehouse.mapper.InventoryMapper;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.InventoryService;
import com.meta.store.werehouse.services.InventoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/inventory")
@RequiredArgsConstructor
public class InventoryController {
	
		
	private final InventoryService inventoryService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	
	@GetMapping("/getbycompany")
	public ResponseEntity<InventoryDto> getInventoryByCompany(){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Long companyId = companyService.findCompanyIdByUserId(userId).getId();
		return inventoryService.getInventoryByCompanyId(companyId);
	}
	
	
}

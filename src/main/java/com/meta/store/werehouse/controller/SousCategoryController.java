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
import com.meta.store.werehouse.dto.SousCategoryDto;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.SousCategory;
import com.meta.store.werehouse.mapper.SousCategoryMapper;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.SousCategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/souscategory")
@RequiredArgsConstructor
public class SousCategoryController {


	private final BaseService<SousCategory, Long> baseService;

	private final SousCategoryMapper sousCategoryMapper;
	
	private final SousCategoryService sousCategoryService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	@GetMapping("/getbycompany")
	public ResponseEntity<List<SousCategoryDto>> getSousCategoryByCompany(){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Long companyId = companyService.findCompanyIdByUserId(userId).getId();
		List<SousCategory> sousCategorys = sousCategoryService.getAllByCompanyId(companyId);
		if(!sousCategorys.isEmpty()) {
		List<SousCategoryDto> sousCategorysDto = new ArrayList<>();
		for(SousCategory i : sousCategorys) {
			SousCategoryDto sousCategoryDto = sousCategoryMapper.mapToDto(i);
			sousCategorysDto.add(sousCategoryDto);
		}
		return ResponseEntity.ok(sousCategorysDto);}
		throw new RecordNotFoundException("there is no sousCategory");
	}
	
	@GetMapping("/l/{name}")
	public ResponseEntity<SousCategoryDto> getSousCategoryById(@PathVariable String name){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		ResponseEntity<SousCategory> sousCategory = sousCategoryService.getByLibelleAndCompanyId(name,company.getId());
		if(sousCategory != null) {
		SousCategoryDto dto = sousCategoryMapper.mapToDto(sousCategory.getBody());
		return ResponseEntity.ok(dto);
		}
		
		else throw new RecordNotFoundException("There Is No SousCategory With Libelle : "+name);
		}
		else throw new RecordNotFoundException("You Have No Company Please Create One :) ");
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<SousCategoryDto> insertSousCategory(@RequestBody @Valid SousCategoryDto sousCategoryDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		ResponseEntity<SousCategory> sousCategory1 = sousCategoryService.getByLibelleAndCompanyId(sousCategoryDto.getLibelle(),company.getId());
		if(sousCategory1 == null)  {
		SousCategory sousCategory = sousCategoryMapper.mapToEntity(sousCategoryDto);
		sousCategory.setCompany(company);
		baseService.insert(sousCategory);
		return new ResponseEntity<SousCategoryDto>(HttpStatus.ACCEPTED);
		}else {
			throw new RecordIsAlreadyExist("is already exist");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<SousCategoryDto> upDateSousCategory(@RequestBody @Valid SousCategoryDto sousCategoryDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		return sousCategoryService.upDateSousCategory(sousCategoryDto,company);
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
	@DeleteMapping("/{id}")
	public void deleteSousCategoryById(@PathVariable Long id){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			Optional<SousCategory> sousCategory = sousCategoryService.getByIdAndCompanyId(id,company.getId());
			if(sousCategory.isPresent()) {
		 baseService.deleteById(id,company.getId());
			}else
			throw new RecordNotFoundException("This SousCategory Does Not Exist");
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
}

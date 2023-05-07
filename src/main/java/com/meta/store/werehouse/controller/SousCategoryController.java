package com.meta.store.werehouse.controller;

import java.lang.module.ModuleDescriptor.Requires;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

	
	private final SousCategoryService sousCategoryService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	@GetMapping("/getbycompany")
	public ResponseEntity<List<SousCategoryDto>> getSousCategoryByCompany(){
		Company company = getCompany();
		return sousCategoryService.getSousCategoryByCompany(company);
	}
	
	@GetMapping("/l/{name}")
	public ResponseEntity<SousCategoryDto> getSousCategoryById(@PathVariable String name){
		Company company = getCompany();
		return sousCategoryService.getSousCategoryById(name,company);
		
	}
	
	@GetMapping("/{categoryId}")
	public List<SousCategoryDto> getAllSousCategoriesByCompanyIdAndCategoryId(@PathVariable Long categoryId){
		Company company = getCompany();
		return sousCategoryService.getAllSousCategoryByCompanyIdAndCategoryId(categoryId, company);
	}
	
	@PostMapping("/add")
	public ResponseEntity<SousCategoryDto> insertSousCategory(@RequestParam("souscategory") SousCategoryDto sousCategoryDto,
			@RequestParam(value = "file",required=false) MultipartFile file){
		Company company = getCompany();
		return sousCategoryService.insertSousCategory(sousCategoryDto,company,file);
	}
	
	@PutMapping("/update")
	public ResponseEntity<SousCategoryDto> upDateSousCategory(
			@RequestParam("souscategory") String sousCategoryDto,
			@RequestParam(value="file",required=false) MultipartFile file) throws JsonMappingException, JsonProcessingException{
		Company company = getCompany();
		return sousCategoryService.upDateSousCategory(sousCategoryDto,company,file);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteSousCategoryById(@PathVariable Long id){
		Company  company = getCompany();
		 sousCategoryService.deleteSousCategoryById(id,company);
	}
	
	private Company getCompany() {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			return company;
		}
			throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
			
	}
}

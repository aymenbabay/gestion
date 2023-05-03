package com.meta.store.werehouse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.meta.store.werehouse.dto.CategoryDto;
import com.meta.store.werehouse.dto.CategoryDto;
import com.meta.store.werehouse.entity.Category;
import com.meta.store.werehouse.entity.Category;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.CategoryMapper;
import com.meta.store.werehouse.services.CategoryService;
import com.meta.store.werehouse.services.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/category")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CategoryController {

	private final BaseService<Category, Long> baseService;
	
	private final CategoryMapper categoryMapper;
	
	private final CategoryService categoryService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	

	@GetMapping("/getbycompany")
	public ResponseEntity<List<CategoryDto>> getCategoryByCompany(){
		
		return categoryService.getCategoryByCompany();
	}
	
	@GetMapping("/l/{name}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String name){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		ResponseEntity<Category> category = categoryService.getByLibelleAndCompanyId(name,company.getId());
		if(category != null) {
		CategoryDto dto = categoryMapper.mapToDto(category.getBody());
		return ResponseEntity.ok(dto);
		}
		
		else throw new RecordNotFoundException("There Is No Category With Libelle : "+name);
		}
		else throw new RecordNotFoundException("You Have No Company Please Create One :) ");
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<CategoryDto> insertCategory(@RequestBody @Valid CategoryDto categoryDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		ResponseEntity<Category> category1 = categoryService.getByLibelleAndCompanyId(categoryDto.getLibelle(),company.getId());
		if(category1 == null)  {
		Category category = categoryMapper.mapToEntity(categoryDto);
		category.setCompany(company);
		baseService.insert(category);
		return new ResponseEntity<CategoryDto>(HttpStatus.ACCEPTED);
		}else {
			throw new RecordIsAlreadyExist("is already exist");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<CategoryDto> upDateCategory(@RequestBody @Valid CategoryDto categoryDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		return categoryService.upDateCategory(categoryDto,company);
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteCategoryById(@PathVariable Long id){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			Optional<Category> category = categoryService.getByIdAndCompanyId(id,company.getId());
			if(category.isPresent()) {
		 baseService.deleteById(id,company.getId());
			}else
			throw new RecordNotFoundException("This Category Does Not Exist");
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
	
}

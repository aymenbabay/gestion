package com.meta.store.werehouse.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.CompanyDto;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.CompanyMapper;
import com.meta.store.werehouse.services.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/company")
@RequiredArgsConstructor
public class CompanyController {

	
	private final CompanyService companyService;
	
	private final CompanyMapper companyMapper;
	
	private final BaseService<Company, Long> baseService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	
	@GetMapping("/all")
	public List<CompanyDto> getAll(){
		List<Company> companys = baseService.getAll();
		if(!companys.isEmpty() && companys != null && !companys.equals(null)) {
		List<CompanyDto> companysDto = new ArrayList<>();
		for(Company i :companys) {
			CompanyDto companyDto = companyMapper.mapToDto(i);
			companysDto.add(companyDto);
		}
		return companysDto;
		}
		throw new RecordNotFoundException("There Is No Company");
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id){
		ResponseEntity<Company> company = baseService.getById(id);
		CompanyDto dto = companyMapper.mapToDto(company.getBody());
		return ResponseEntity.ok(dto);
		
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<CompanyDto> insertCompany(@RequestBody @Valid CompanyDto companyDto){
		AppUser userId = appUserService.findByUserName(authenticationFilter.userName);
		long exist = companyService.countCompanyByUserId(userId.getId());
		long name = companyService.countCompanyByName(companyDto.getCompanyName());
		if(exist == 0 && name == 0) {
		Company company = companyMapper.mapToEntity(companyDto);
		company.setUser(userId);
		baseService.insert(company);
		return new ResponseEntity<CompanyDto>(HttpStatus.ACCEPTED);
		}
		if(exist != 0) {
			throw new RecordIsAlreadyExist("You Already have A Company");
		}
		throw new RecordIsAlreadyExist("This Name Is Already Exist Please Choose Another One");
	}
	
	@PutMapping("/update")
	public ResponseEntity<CompanyDto> upDateCompany(@RequestBody @Valid CompanyDto companyDto){
		return companyService.upDateCompany(companyDto);
	}
	
	//must be do not delete
	/*
	@DeleteMapping("/{id}")
	public void deleteCompanyById(@PathVariable Long id){
		AppUser userId = appUserService.findByUserName(authenticationFilter.userName);
		 companyService.deleteByIdAndUserId(id,userId);
	}
	*/
}

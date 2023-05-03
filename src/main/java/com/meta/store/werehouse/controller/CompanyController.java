package com.meta.store.werehouse.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.werehouse.dto.CompanyDto;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.WorkerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/company")
@RequiredArgsConstructor
public class CompanyController {

	
	private final CompanyService companyService;
		
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final WorkerService workerService;
	
	@GetMapping("/all")
	public List<CompanyDto> getAll(){
		return companyService.getAllCompany();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CompanyDto> getCompanyById(@PathVariable Long id){
		return companyService.getCompanyById(id);		
	}
	
	@GetMapping("/mycompany")
	public CompanyDto getMe() {
		Company company = getCompany();
		return companyService.getMe(company);
	}

	
	private Company getCompany() {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("you have no company");
		}
		return company;
	}

	@GetMapping("/has_company")
	public boolean hasCompany() {
		System.out.println("11111111111111111111111111111111111111");
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		System.out.println("11111111111111111111111111111111111111"+userId);
		Company company = companyService.findCompanyIdByUserId(userId);
		System.out.println("11111111111111111111111111111111111111company");
		if(company == null) {
			System.out.println("11111111111111111111111111111111111111companynull");
			Long companyId = workerService.getByName(authenticationFilter.userName);
			System.out.println("111111111111111111111111111111111111112check"+companyId);
			if(companyId != null) {
				System.out.println("11111111111111111111111111111111111111"+companyId);
				return true;
			}
			return false;
		}
		return true;
	}
	
	@PostMapping("/add")
	public ResponseEntity<CompanyDto> insertCompany( 
			@RequestParam("company") @Valid String company,
			@RequestParam("file") MultipartFile file)throws Exception{
		AppUser user = appUserService.findByUserName(authenticationFilter.userName);
		return companyService.insertCompany(company, file, user);
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

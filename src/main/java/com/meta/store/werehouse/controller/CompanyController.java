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

	@GetMapping("/hascompany")
	public boolean hasCompany() {
		Company company = getCompany();
		if(company == null) {
			Long companyId = workerService.getByName(authenticationFilter.userName);
			if(companyId != null) {
				return true;
			}
			return false;
		}
		return true;
	}
	
	@PostMapping("/add")
	public ResponseEntity<CompanyDto> insertCompany( 
			@RequestParam("company") String company,
			@RequestParam("file") MultipartFile file)throws Exception{
		AppUser user = appUserService.findByUserName(authenticationFilter.userName);
		return companyService.insertCompany(company, file, user);
		}
	
	@PutMapping("/update")
	public ResponseEntity<CompanyDto> upDateCompany(@RequestBody CompanyDto companyDto){
		return companyService.upDateCompany(companyDto);
	}
	
	@GetMapping("/rate/{id}/{rate}")
	public void rateCompany(@PathVariable long id, @PathVariable long rate) {
		companyService.rateCompany(id,rate);
	}
	
	private Company getCompany() {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("you have no company");
		}
		return company;
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

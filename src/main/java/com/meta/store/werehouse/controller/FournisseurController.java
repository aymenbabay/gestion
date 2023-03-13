package com.meta.store.werehouse.controller;

import java.util.List;

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

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.werehouse.dto.FournisseurDto;
import com.meta.store.werehouse.dto.FournisseurDto2;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.FournisseurService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/fournisseur")
@RequiredArgsConstructor
public class FournisseurController {

		
	private final FournisseurService fournisseurService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	
	
	@PostMapping("/add")
	public ResponseEntity<FournisseurDto> insertFournisseur(@RequestBody  FournisseurDto fournisseurDto){
		System.out.println("cooooooooooooooooooooooooode");
		System.out.println(fournisseurDto.getCode()+"cooooooooooooooooooooooooode");
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return fournisseurService.insertFournisseur(fournisseurDto, company);
	}
	
	@PostMapping("/add_exist/{id}")
	public ResponseEntity<String> addExistFournisseur(@PathVariable Long id){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return fournisseurService.addExistFournisseur(id,company);
	}
	
	@PostMapping("/add_me")
	public ResponseEntity<FournisseurDto2> addMeAsFournisseur(@RequestBody FournisseurDto2 fournisseurDto){
		AppUser user = appUserService.findByUserName(authenticationFilter.userName);
		Company company = companyService.findCompanyIdByUserId(user.getId());
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return fournisseurService.addMeAsFournisseur(fournisseurDto,user,company);
	}
	
	@GetMapping("/get_all_my")
	public List<FournisseurDto2> getMybyCompany() {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return fournisseurService.getMybyCompanyId(company);
	}
	
	@GetMapping("/get_my_by_code/{code}")
	public FournisseurDto getMyByCode(@PathVariable @Valid String code) {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return fournisseurService.getMyByCodeAndCompanyId(code,company);
	}

	@GetMapping("/get_my_by_name/{name}")
	public List<FournisseurDto> getMyByName(@PathVariable @Valid String name) {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return fournisseurService.getMyByNameAndCompanyId(name,company);
	}
	
	@GetMapping("/get_all")
	public List<FournisseurDto> getAll() {
		return fournisseurService.getAllFournisseur();
				
	}
	
	@GetMapping("/get_all_by_code/{code}")
	public FournisseurDto getAllByCode(@PathVariable String code) {
		return fournisseurService.getFournisseurByCode(code);
				
	}
	
	@GetMapping("/get_all_by_name/{name}")
	public List<FournisseurDto> getAllByName(@PathVariable String name) {
		return fournisseurService.getAllFournisseurByName(name);
				
	}
	
	@PutMapping("/update/{id}")
	public FournisseurDto upDateMyFournisseurById(@PathVariable Long id, @RequestBody FournisseurDto fournisseurDto) {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return fournisseurService.upDateMyFournisseurById(id,fournisseurDto,company,userId,authenticationFilter.userName);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteFournisseur(@PathVariable Long id) {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		fournisseurService.deleteFournisseurById(id,userId,authenticationFilter.userName,company);
		
	}

}

package com.meta.store.werehouse.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.meta.store.werehouse.dto.ClientDto;
import com.meta.store.werehouse.dto.ClientDto2;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.ClientMapper;
import com.meta.store.werehouse.services.ClientService;
import com.meta.store.werehouse.services.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/client")
@RequiredArgsConstructor
public class ClientController {

	
	private final ClientService clientService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	
	
	@PostMapping("/add")
	public ResponseEntity<ClientDto> insertClient(@RequestBody @Valid ClientDto clientDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return clientService.insertClient(clientDto, company);
	}
	
	@PostMapping("/add_exist/{id}")
	public ResponseEntity<String> addExistClient(@PathVariable Long id){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return clientService.addExistClient(id,company);
	}
	
	@PostMapping("/add_me")
	public ResponseEntity<ClientDto2> addMeAsClient(@RequestBody ClientDto2 clientDto){
		AppUser user = appUserService.findByUserName(authenticationFilter.userName);
		Company company = companyService.findCompanyIdByUserId(user.getId());
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return clientService.addMeAsClient(clientDto,user,company);
	}
	
	@GetMapping("/get_all_my")
	public List<ClientDto2> getMybyCompany() {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return clientService.getMybyCompanyId(company);
	}
	
	@GetMapping("/get_my_by_code/{code}")
	public ClientDto getMyByCode(@PathVariable @Valid String code) {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return clientService.getMyByCodeAndCompanyId(code,company);
	}

	@GetMapping("/get_my_by_name/{name}")
	public List<ClientDto> getMyByName(@PathVariable @Valid String name) {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return clientService.getMyByNameAndCompanyId(name,company);
	}
	
	@GetMapping("/get_all")
	public List<ClientDto> getAll() {
		return clientService.getAllClient();
				
	}
	
	@GetMapping("/get_all_by_code/{code}")
	public ClientDto getAllByCode(@PathVariable String code) {
		return clientService.getClientByCode(code);
				
	}
	
	@GetMapping("/get_all_by_name/{name}")
	public List<ClientDto> getAllByName(@PathVariable String name) {
		return clientService.getAllClientByName(name);
				
	}
	
	@PutMapping("/update/{id}")
	public ClientDto upDateMyClientById(@PathVariable Long id, @RequestBody ClientDto clientDto) {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		return clientService.upDateMyClientById(id,clientDto,company,userId,authenticationFilter.userName);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteClient(@PathVariable Long id) {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company == null) {
			throw new RecordNotFoundException("You Do Not Have A Company");
		}
		clientService.deleteClientById(id,userId,authenticationFilter.userName,company);
		
	}

}








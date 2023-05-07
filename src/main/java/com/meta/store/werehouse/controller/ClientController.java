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
		return clientService.insertClient(clientDto);
	}
	
	@GetMapping("/add_exist/{id}")
	public ResponseEntity<String> addExistClient(@PathVariable Long id){
		Company company = getCompany();
		return clientService.addExistClient(id,company);
	}
	
	@PostMapping("/add_me_exist")
	public ResponseEntity<ClientDto2> addMeAsClientExist(@RequestBody ClientDto2 clientDto){
		Company company = getCompany();
		return clientService.addMeAsClientExist(clientDto,company);
	}
	
	@GetMapping("/add_me/{code}")
	public void addMeAsClient(@PathVariable String code) {
		Company company = getCompany();
		clientService.addMeAsClient(company, code);
		
	}
	
	@GetMapping("/get_all_my")
	public List<ClientDto> getMybyCompany() {
		Company company = getCompany();
		return clientService.getMybyCompanyId(company);
	}
	
	@GetMapping("/get_my_by_code/{code}")
	public ClientDto getMyByCode(@PathVariable @Valid String code) {
		Company company = getCompany();
		return clientService.getMyByCodeAndCompanyId(code,company);
	}

	@GetMapping("/get_my_by_name/{name}")
	public List<ClientDto> getMyByName(@PathVariable @Valid String name) {
		Company company = getCompany();
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
		Company company = getCompany();
		return clientService.upDateMyClientById(id,clientDto,company,company.getUser().getId(),authenticationFilter.userName);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteClient(@PathVariable Long id) {
		Company company = getCompany();
		clientService.deleteClientById(id,company.getUser().getId(),company);
		
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








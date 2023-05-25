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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.store.base.error.RecordIsAlreadyExist;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.VacationDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Vacation;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.mapper.WorkerMapper;
import com.meta.store.werehouse.repository.VacationRepository;
import com.meta.store.werehouse.repository.WorkerRepository;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.WorkerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/worker")
@RequiredArgsConstructor
public class WorkerController {
	
	
	private final WorkerService workerService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	
	@GetMapping("/getbycompany")
	public ResponseEntity<List<WorkerDto>> getWorkerByCompany(){
		Company company = getCompany();
		return workerService.getWorkerByCompany(company);
	}
	
	@GetMapping("/l/{name}")
	public ResponseEntity<WorkerDto> getWorkerById(@PathVariable String name){
		Company company = getCompany();
		return workerService.getWorkerById(name,company);
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<WorkerDto> insertWorker(@RequestBody @Valid WorkerDto workerDto){
		Company company = getCompany();
		return workerService.insertWorker(workerDto,company);
	}
	
	@PutMapping("/update")
	public ResponseEntity<WorkerDto> upDateWorker(@RequestBody @Valid WorkerDto workerDto){
		Company company = getCompany();
		return workerService.upDateWorker(workerDto,company);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteWorkerById(@PathVariable Long id){
		Company company = getCompany();
			workerService.deleteWorkerById(id,company);
		}
	
	@PostMapping("/addvacation")
	public void addVacation( @RequestBody VacationDto vacationDto) {
		Company company = getCompany();
		workerService.addVacation(vacationDto,company);
		}
	
	@GetMapping("/history/{id}")
	public List<VacationDto> getWorkerHistory(@PathVariable Long id) {
		Company company = getCompany();
		return workerService.getWorkerHistory(company,id);
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

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
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.mapper.WorkerMapper;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.WorkerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/worker")
@RequiredArgsConstructor
public class WorkerController {
	
private final BaseService<Worker, Long> baseService;
	
	private final WorkerMapper workerMapper;
	
	private final WorkerService workerService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	private final CompanyService companyService;
	
	@GetMapping("/getbycompany")
	public ResponseEntity<List<WorkerDto>> getWorkerByCompany(){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Long companyId = companyService.findCompanyIdByUserId(userId).getId();
		List<Worker> workers = workerService.getAllByCompanyId(companyId);
		if(!workers.isEmpty()) {
		List<WorkerDto> workersDto = new ArrayList<>();
		for(Worker i : workers) {
			WorkerDto workerDto = workerMapper.mapToDto(i);
			workersDto.add(workerDto);
		}
		return ResponseEntity.ok(workersDto);}
		throw new RecordNotFoundException("there is no worker");
	}
	
	@GetMapping("/l/{name}")
	public ResponseEntity<WorkerDto> getWorkerById(@PathVariable String name){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		ResponseEntity<Worker> worker = workerService.getByNameAndCompanyId(name,company.getId());
		if(worker != null) {
		WorkerDto dto = workerMapper.mapToDto(worker.getBody());
		return ResponseEntity.ok(dto);
		}
		
		else throw new RecordNotFoundException("There Is No Worker With Libelle : "+name);
		}
		else throw new RecordNotFoundException("You Have No Company Please Create One :) ");
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<WorkerDto> insertWorker(@RequestBody @Valid WorkerDto workerDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		ResponseEntity<Worker> worker1 = workerService.getByNameAndCompanyId(workerDto.getName(),company.getId());
		if(worker1 == null)  {
		Worker worker = workerMapper.mapToEntity(workerDto);
		worker.setCompany(company);
		baseService.insert(worker);
		return new ResponseEntity<WorkerDto>(HttpStatus.ACCEPTED);
		}else {
			throw new RecordIsAlreadyExist("is already exist");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<WorkerDto> upDateWorker(@RequestBody @Valid WorkerDto workerDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		return workerService.upDateWorker(workerDto,company);
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
	@DeleteMapping("/{id}")
	public void deleteWorkerById(@PathVariable Long id){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			Optional<Worker> worker = workerService.getByIdAndCompanyId(id,company.getId());
			if(worker.isPresent()) {
		 baseService.deleteById(id,company.getId());
			}else
			throw new RecordNotFoundException("This Worker Does Not Exist");
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
}

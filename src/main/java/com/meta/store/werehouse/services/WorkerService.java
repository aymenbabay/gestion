package com.meta.store.werehouse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.mapper.WorkerMapper;
import com.meta.store.werehouse.repository.WorkerRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService  extends BaseService<Worker, Long>{
	
	
	private final WorkerMapper workerMapper;
	
	private final WorkerRepository workerRepository;
	
	public ResponseEntity<WorkerDto> upDateWorker( WorkerDto workerDto, Company company) {
		Optional<Worker> worker = workerRepository.findByIdAndCompanyId(workerDto.getId(),company.getId());
		if(worker.isPresent()) {
			Worker categ = workerMapper.mapToEntity(workerDto);
			categ.setCompany(company);
			workerRepository.save(categ);
			return ResponseEntity.ok(workerDto);
			
		}else {
			throw new RecordNotFoundException("Worker Not Found");
		}
	}

	public Optional<Worker> getByName(String libelle, Long companyId) {
		return workerRepository.findByNameAndCompanyId(libelle, companyId);
	}

	public List<Worker> getAllByCompanyId(Long companyId) {
		return workerRepository.findAllByCompanyId(companyId);
	}

	public ResponseEntity<Worker> getByNameAndCompanyId(String name, Long companyId) {
		Optional<Worker> categ = workerRepository.findByNameAndCompanyId(name,companyId);
		if(!categ.isEmpty()) {
		Worker worker = categ.get();
		return ResponseEntity.ok(worker);
		}
		else return null;
	}
	
	public Optional<Worker> getByIdAndCompanyId(Long id , Long companyId) {
		return workerRepository.findByIdAndCompanyId(id, companyId);
	}

	public Long getByName(String name) {
		return workerRepository.findByName(name);
	}

	public Long getCompanyIdByUserName(String userName) {
		Long companyId = workerRepository.findByName(userName);
		if(companyId != null) {
			return companyId;
		}
		return null;
	}
	
}

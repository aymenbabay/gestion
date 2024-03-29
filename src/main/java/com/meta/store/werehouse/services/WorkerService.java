package com.meta.store.werehouse.services;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordIsAlreadyExist;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.VacationDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.dto.WorkerDto;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Vacation;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.entity.Worker;
import com.meta.store.werehouse.mapper.VacationMapper;
import com.meta.store.werehouse.mapper.WorkerMapper;
import com.meta.store.werehouse.repository.VacationRepository;
import com.meta.store.werehouse.repository.WorkerRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class WorkerService  extends BaseService<Worker, Long>{
	
	
	private final WorkerMapper workerMapper;
	
	private final VacationMapper vacationMapper ;
	
	private final WorkerRepository workerRepository;

	private final VacationRepository vacationRepository;

	private final AppUserService appUserService;

	
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

	public ResponseEntity<List<WorkerDto>> getWorkerByCompany(Company company) {
		List<Worker> workers = getAllByCompanyId(company.getId());
		if(workers.isEmpty()) {
			throw new RecordNotFoundException("there is no worker");
		}
		List<WorkerDto> workersDto = new ArrayList<>();
		for(Worker i : workers) {
			WorkerDto workerDto = workerMapper.mapToDto(i);
			workersDto.add(workerDto);
		}
		return ResponseEntity.ok(workersDto);
	}

	public ResponseEntity<WorkerDto> getWorkerById(String name, Company company) {
		ResponseEntity<Worker> worker = getByNameAndCompanyId(name,company.getId());
		if(worker == null) {
			 throw new RecordNotFoundException("There Is No Worker With Libelle : "+name);
		}
		WorkerDto dto = workerMapper.mapToDto(worker.getBody());
		return ResponseEntity.ok(dto);
		
	}

	public ResponseEntity<WorkerDto> insertWorker(@Valid WorkerDto workerDto, Company company) {
		Long worker1 = getByName(workerDto.getName());
		if(worker1 !=null)  {
			throw new RecordIsAlreadyExist("is already exist");
		}
		
		AppUser user = appUserService.findByUserName(workerDto.getName());
		Worker worker = workerMapper.mapToEntity(workerDto);
		worker.setName(workerDto.getName());
		worker.setUser(user);
		worker.setCompany(company);
		super.insert(worker);
		return new ResponseEntity<WorkerDto>(HttpStatus.ACCEPTED);
		
	}

	public void deleteWorkerById(Long id, Company company) {
		Optional<Worker> worker = getByIdAndCompanyId(id,company.getId());
		if(worker.isEmpty()) {
			throw new RecordNotFoundException("This Worker Does Not Exist");
		}
	 super.deleteById(id,company.getId());
	}

	public void addVacation(VacationDto vacationDto, Company company) {
		ResponseEntity<Worker> worker = super.getById(vacationDto.getWorker().getId());
		Vacation vacation = vacationMapper.mapToEntity(vacationDto);
		long differenceInDays = TimeUnit.DAYS.convert(vacation.getEnddate().getTime() - vacation.getStartdate().getTime(), TimeUnit.MILLISECONDS);
		int year = getYearFromDate(vacation.getStartdate());
		vacation.setRemainingday(vacation.getRemainingday()-differenceInDays);  
		vacation.setUsedday(vacation.getUsedday()+differenceInDays);
		vacation.setYear(year);
		vacation.setWorker(worker.getBody());
		vacation.setCompany(company);
		vacationRepository.save(vacation);
	}

	public List<VacationDto> getWorkerHistory(Company company, Long id) {
		List<Vacation> vacations = vacationRepository.findByCompanyIdAndWorkerId(company.getId(),id);
		List<VacationDto> vacationsDto = new ArrayList<>();
		for(Vacation i : vacations) {
			VacationDto vacationDto = vacationMapper.mapToDto(i);
			vacationsDto.add(vacationDto);
		}
 		return vacationsDto;
	}
	
	  private static int getYearFromDate(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.YEAR);
	    }
	
}

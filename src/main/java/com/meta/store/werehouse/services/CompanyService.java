package com.meta.store.werehouse.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.base.error.RecordIsAlreadyExist;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.CompanyDto;
import com.meta.store.werehouse.dto.CompanyDto;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.ClientMapper;
import com.meta.store.werehouse.mapper.CompanyMapper;
import com.meta.store.werehouse.repository.ClientRepository;
import com.meta.store.werehouse.repository.CompanyRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService extends BaseService<Company, Long>{
 

	private final CompanyMapper companyMapper;
	
	private final CompanyRepository companyRepository;
	
	
	public long countCompanyByUserId(Long id) {
		return companyRepository.countByUserId(id);
	}

	public long countCompanyByName(String companyName) {
		return companyRepository.countByCompanyName(companyName);
	}
	
	/*
	public void deleteByIdAndUserId(Long id, AppUser userId) {
		Long exist = companyRepository.countByUserId(userId.getId());
		if(exist>0) {
			companyRepository.deleteByIdAndUserId(id,userId.getId());
		}else {
		throw new RecordNotFoundException("You Have No Company Thank You To Create One");
		}
	}
	*/
	
	public ResponseEntity<CompanyDto> upDateCompany(@Valid CompanyDto companyDto) {
		ResponseEntity<Company> company = super.getById(companyDto.getId());
		if(!company.hasBody()) {
			throw new RecordNotFoundException("You Have No Company Yet");
		}
			Company compani = companyMapper.mapToEntity(companyDto);
			compani.setUser(company.getBody().getUser());
			compani.setIdentityNumber(company.getBody().getIdentityNumber());
			compani.setNational_id_number(company.getBody().getNational_id_number());
			compani.setCompanyName(companyDto.getCompanyName());
			companyRepository.save(compani);
			return ResponseEntity.ok(companyDto);
			
		
	}
	
	public Company findCompanyIdByUserId(Long userId) {
		return companyRepository.findByUserId(userId);
	}
}

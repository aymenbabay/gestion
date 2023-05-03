package com.meta.store.werehouse.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.store.base.error.RecordIsAlreadyExist;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.base.security.entity.Role;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.security.service.RoleService;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.CompanyDto;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.CompanyMapper;
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

	private final AppUserService appUserService;
		
	private final RoleService roleService;

	private final ImageService imageService;
	
	
	public long countCompanyByUserId(Long id) {
		return companyRepository.countByUserId(id);
	}

	public long countCompanyByName(String companyName) {
		return companyRepository.countByName(companyName);
	}

	
	public ResponseEntity<CompanyDto> upDateCompany(@Valid CompanyDto companyDto) {
		ResponseEntity<Company> company = super.getById(companyDto.getId());
		if(!company.hasBody()) {
			throw new RecordNotFoundException("You Have No Company Yet");
		}
			Company compani = companyMapper.mapToEntity(companyDto);
			compani.setUser(company.getBody().getUser());
		//	compani.setIdentityNumber(company.getBody().getIdentityNumber());
		//	compani.setNationalIdNumber(company.getBody().getNationalIdNumber());
			compani.setName(companyDto.getName());
			companyRepository.save(compani);
			return ResponseEntity.ok(companyDto);
			
		
	}
	
	public Company findCompanyIdByUserId(Long userId) {
		return companyRepository.findByUserId(userId);
	}

	public CompanyDto getMe(Company company) {
		CompanyDto companyDto = companyMapper.mapToDto(company);
		return companyDto;
	}

	public List<CompanyDto> getAllCompany() {
		List<Company> companies = super.getAll();
		if(!companies.isEmpty() && companies != null && !companies.equals(null)) {
		List<CompanyDto> companysDto = new ArrayList<>();
		for(Company i :companies) {
			CompanyDto companyDto = companyMapper.mapToDto(i);
			companysDto.add(companyDto);
		}
		return companysDto;
		}
		throw new RecordNotFoundException("There Is No Company");
	}

	public ResponseEntity<CompanyDto> getCompanyById(Long id) {
		ResponseEntity<Company> company = super.getById(id);
		CompanyDto dto = companyMapper.mapToDto(company.getBody());
		return ResponseEntity.ok(dto);
	}

	public ResponseEntity<CompanyDto> insertCompany(@Valid String company, MultipartFile file, AppUser user) throws JsonMappingException, JsonProcessingException {
		long exist = countCompanyByUserId(user.getId());
		
		CompanyDto companyDto = new ObjectMapper().readValue(company, CompanyDto.class);
		long name = countCompanyByName(companyDto.getName());
		if(exist == 0 && name == 0) {
			Company company1 = companyMapper.mapToEntity(companyDto);
			if(file != null) {
				
		String newFileName = imageService.insertImag(file, user.getUsername(), "company");
		company1.setLogo(newFileName);
			}
		company1.setUser(user);
		super.insert(company1);
		Set<Role> role = new HashSet<>();
		ResponseEntity<Role> role2 = roleService.getById((long)1);
		System.out.println(role2.getBody().getName());
		role.add(role2.getBody());
		role.addAll(user.getRoles());
		user.setRoles(role);
		appUserService.save(user);
		return new ResponseEntity<CompanyDto>(HttpStatus.ACCEPTED);
		}
		if(exist != 0) {
			throw new RecordIsAlreadyExist("You Already have A Company");
		}
		throw new RecordIsAlreadyExist("This Name Is Already Exist Please Choose Another One");
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
}

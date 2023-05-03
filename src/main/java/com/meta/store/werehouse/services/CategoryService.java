package com.meta.store.werehouse.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.CategoryDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Category;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.CategoryMapper;
import com.meta.store.werehouse.repository.CategoryRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService extends BaseService<Category, Long> {
	
	
	private final CategoryMapper categoryMapper;
	
	private final CategoryRepository categoryRepository;
	
	private final ImageService imageService; // i will use it

	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	

	
	public ResponseEntity<CategoryDto> upDateCategory( CategoryDto categoryDto, Company company) {
		Optional<Category> category = categoryRepository.findByIdAndCompanyId(categoryDto.getId(),company.getId());
		if(category.isPresent()) {
			Category categ = categoryMapper.mapToEntity(categoryDto);
			categ.setCompany(company);
			categoryRepository.save(categ);
			return ResponseEntity.ok(categoryDto);
			
		}else {
			throw new RecordNotFoundException("Category Not Found");
		}
	}

	public Optional<Category> getByLibelle(String libelle, Long companyId) {
		return categoryRepository.findByLibelleAndCompanyId(libelle, companyId);
	}

	public List<Category> getAllByCompanyId(Long companyId) {
		return categoryRepository.findAllByCompanyId(companyId);
	}

	public ResponseEntity<Category> getByLibelleAndCompanyId(String name, Long companyId) {
		Optional<Category> categ = categoryRepository.findByLibelleAndCompanyId(name,companyId);
		if(!categ.isEmpty()) {
		Category category = categ.get();
		return ResponseEntity.ok(category);
		}
		else return null;
	}
	
	public Optional<Category> getByIdAndCompanyId(Long id , Long companyId) {
		return categoryRepository.findByIdAndCompanyId(id, companyId);
	}
	

	private Company getCompany() {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			return company;
		}
			throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
			
	}

	public ResponseEntity<List<CategoryDto>> getCategoryByCompany() {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Long companyId = companyService.findCompanyIdByUserId(userId).getId();
		List<Category> categorys = getAllByCompanyId(companyId);
		if(!categorys.isEmpty()) {
		List<CategoryDto> categorysDto = new ArrayList<>();
		for(Category i : categorys) {
			CategoryDto categoryDto = categoryMapper.mapToDto(i);
			categorysDto.add(categoryDto);
		}
		return ResponseEntity.ok(categorysDto);}
		throw new RecordNotFoundException("there is no category");
	}
}

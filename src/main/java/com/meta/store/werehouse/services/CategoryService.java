package com.meta.store.werehouse.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordIsAlreadyExist;
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
	

	
	public ResponseEntity<CategoryDto> upDateCategory( CategoryDto categoryDto, Company company) {
		Optional<Category> category = categoryRepository.findByIdAndCompanyId(categoryDto.getId(),company.getId());
		if(category.isEmpty()) {
			throw new RecordNotFoundException("Category Not Found");
		}
			Category categ = categoryMapper.mapToEntity(categoryDto);
			categ.setCompany(company);
			categoryRepository.save(categ);
			return ResponseEntity.ok(categoryDto);
			
	}

	public Optional<Category> getByLibelle(String libelle, Long companyId) {
		return categoryRepository.findByLibelleAndCompanyId(libelle, companyId);
	}

	public List<Category> getAllByCompanyId(Long companyId) {
		return categoryRepository.findAllByCompanyId(companyId);
	}

	public ResponseEntity<Category> getByLibelleAndCompanyId(String name, Long companyId) {
		Optional<Category> categ = categoryRepository.findByLibelleAndCompanyId(name,companyId);
		if(categ.isEmpty()) {
			throw new RecordNotFoundException("There is no category with libelle: "+name);
		}
		Category category = categ.get();
		return ResponseEntity.ok(category);
		
	}
	
	public Optional<Category> getByIdAndCompanyId(Long id , Long companyId) {
		return categoryRepository.findByIdAndCompanyId(id, companyId);
	}
	

	

	public List<CategoryDto> getCategoryByCompany(Company company) {
		List<Category> categorys = getAllByCompanyId(company.getId());
		if(categorys.isEmpty()) {
			throw new RecordNotFoundException("there is no category");
		}
		List<CategoryDto> categorysDto = new ArrayList<>();
		for(Category i : categorys) {
			CategoryDto categoryDto = categoryMapper.mapToDto(i);
			categorysDto.add(categoryDto);
		}
		return categorysDto;
	}

	public CategoryDto getByLibelleAndCompanyId(Company company, String name) {
		Optional<Category> category = categoryRepository.findByLibelleAndCompanyId(name,company.getId());
		if(category.isEmpty()) {
			throw new RecordNotFoundException("There Is No Category With Libelle : "+name);
		}
		CategoryDto dto = categoryMapper.mapToDto(category.get());
		return dto;
	}

	public ResponseEntity<CategoryDto> insertCategory( CategoryDto categoryDto, Company company) {
		ResponseEntity<Category> category1 = getByLibelleAndCompanyId(categoryDto.getLibelle(),company.getId());
		if(category1 != null)  {
			throw new RecordIsAlreadyExist("Category "+categoryDto.getLibelle()+"is already exist");
		}
		Category category = categoryMapper.mapToEntity(categoryDto);
		category.setCompany(company);
		super.insert(category);
		return new ResponseEntity<CategoryDto>(HttpStatus.ACCEPTED);
	}

	public void deleteCategoryById(Long id, Company company) {
		Optional<Category> category = getByIdAndCompanyId(id,company.getId());
		if(category.isEmpty()) {
			throw new RecordNotFoundException("This Category Does Not Exist");
		}
		super.deleteById(id,company.getId());		
	}
}
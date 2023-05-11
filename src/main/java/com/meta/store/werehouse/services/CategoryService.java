package com.meta.store.werehouse.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
	

	
	public ResponseEntity<CategoryDto> upDateCategory( String catDto, Company company, MultipartFile file) throws JsonMappingException, JsonProcessingException {
		CategoryDto categoryDto = new ObjectMapper().readValue(catDto, CategoryDto.class);
		Optional<Category> category = categoryRepository.findByIdAndCompanyId(categoryDto.getId(),company.getId());
		if(category.isEmpty()) {
			throw new RecordNotFoundException("Category Not Found");
		}
			Category categ = categoryMapper.mapToEntity(categoryDto);
			if(file != null) {

				String newFileName = imageService.insertImag(file,company.getUser().getUsername(), "category");
				categ.setImage(newFileName);
			}
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

	public ResponseEntity<CategoryDto> insertCategory( String catDto, Company company, MultipartFile file)
			throws JsonMappingException, JsonProcessingException {

		System.out.println("insert service"+catDto);
		CategoryDto categoryDto = new ObjectMapper().readValue(catDto, CategoryDto.class);
		Optional<Category> category1 = categoryRepository.findByLibelleAndCompanyId(categoryDto.getLibelle(),company.getId());
		if(category1.isPresent())  {
			throw new RecordIsAlreadyExist("Category "+categoryDto.getLibelle()+"is already exist");
		}
		Category category = categoryMapper.mapToEntity(categoryDto);
		if(file !=null) {

			String newFileName = imageService.insertImag(file,company.getUser().getUsername(), "category");
			category.setImage(newFileName);
		}
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
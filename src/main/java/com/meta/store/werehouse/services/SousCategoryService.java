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
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.dto.SousCategoryDto;
import com.meta.store.werehouse.entity.SousCategory;
import com.meta.store.werehouse.entity.Category;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.SousCategoryMapper;
import com.meta.store.werehouse.repository.SousCategoryRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SousCategoryService  extends BaseService<SousCategory, Long> {
	
	
	
	private final SousCategoryMapper sousCategoryMapper;
	
	private final SousCategoryRepository sousCategoryRepository;
	
	private final CategoryService categoryService;
	
	private final ImageService imageService;
	
	
	public ResponseEntity<SousCategoryDto> upDateSousCategory( String dto, Company company, MultipartFile file) throws JsonMappingException, JsonProcessingException {
		SousCategoryDto sousCategoryDto = new ObjectMapper().readValue(dto, SousCategoryDto.class);
		Optional<SousCategory> sousCategory = sousCategoryRepository.findByIdAndCompanyId(sousCategoryDto.getId(),company.getId());
		if(sousCategory.isPresent()) {
			SousCategory categ = sousCategoryMapper.mapToEntity(sousCategoryDto);
			if(sousCategoryDto.getCategory() != sousCategory.get().getCategory()) {
				ResponseEntity<Category> category = categoryService.getById(sousCategoryDto.getCategory().getId());
				if(category == null) {
					throw new RecordNotFoundException("there is no category with libelle: "+sousCategoryDto.getCategory().getLibelle());
				}
				categ.setCategory(category.getBody());
			}
			if(file != null) {

				String newFileName = imageService.insertImag(file,company.getUser().getUsername(), "souscategory");
				categ.setImage(newFileName);
			}
			categ.setCompany(company);
			sousCategoryRepository.save(categ);
			return ResponseEntity.ok(sousCategoryDto);
			
			
		}else {
			throw new RecordNotFoundException("SousCategory Not Found");
		}
	}

	public Optional<SousCategory> getByLibelle(String libelle, Long companyId) {
		return sousCategoryRepository.findByLibelleAndCompanyId(libelle, companyId);
	}

	public List<SousCategory> getAllByCompanyId(Long companyId) {
		return sousCategoryRepository.findAllByCompanyId(companyId);
	}

	public ResponseEntity<SousCategory> getByLibelleAndCompanyId(String name, Long companyId) {
		Optional<SousCategory> categ = sousCategoryRepository.findByLibelleAndCompanyId(name,companyId);
		if(!categ.isEmpty()) {
		SousCategory sousCategory = categ.get();
		return ResponseEntity.ok(sousCategory);
		}
		else return null;
	}
	
	public Optional<SousCategory> getByIdAndCompanyId(Long id , Long companyId) {
		return sousCategoryRepository.findByIdAndCompanyId(id, companyId);
	}

	public List<SousCategoryDto> getByCompanyIdAndCategoryId(Long id, Long categoryId) {

		List<SousCategory> sousCategory = sousCategoryRepository.findAllByCompanyIdAndCategoryId(id,categoryId);
		if(sousCategory.isEmpty()) {
			throw new RecordNotFoundException("there is no sous category inside this category");
		}
		List<SousCategoryDto> listSousCategoryDto = new ArrayList<>();
		for(SousCategory i: sousCategory) {
			SousCategoryDto sousCategoryDto = sousCategoryMapper.mapToDto(i);
			listSousCategoryDto.add(sousCategoryDto);
		}
		return listSousCategoryDto;
		
	}

	public ResponseEntity<List<SousCategoryDto>> getSousCategoryByCompany(Company company) {
		List<SousCategory> sousCategorys = getAllByCompanyId(company.getId());
		if(sousCategorys.isEmpty()) {
			throw new RecordNotFoundException("there is no sousCategory");
		}
		List<SousCategoryDto> sousCategorysDto = new ArrayList<>();
		for(SousCategory i : sousCategorys) {
			SousCategoryDto sousCategoryDto = sousCategoryMapper.mapToDto(i);
			sousCategorysDto.add(sousCategoryDto);
		}
		return ResponseEntity.ok(sousCategorysDto);
		}

	public ResponseEntity<SousCategoryDto> getSousCategoryById(String name, Company company) {
		ResponseEntity<SousCategory> sousCategory = getByLibelleAndCompanyId(name,company.getId());
		if(sousCategory == null) {
			 throw new RecordNotFoundException("There Is No SousCategory With Libelle : "+name);
		}
		SousCategoryDto dto = sousCategoryMapper.mapToDto(sousCategory.getBody());
		return ResponseEntity.ok(dto);
		
	}

	public List<SousCategoryDto> getAllSousCategoryByCompanyIdAndCategoryId(Long categoryId, Company company) {
		List<SousCategoryDto> sousCategoryDto = getByCompanyIdAndCategoryId(company.getId(),categoryId);
		if(sousCategoryDto == null) {
			throw new RecordNotFoundException("there is no sous category inside this category");
		}
		return sousCategoryDto;
	}

	public ResponseEntity<SousCategoryDto> insertSousCategory(String sousCatDto, Company company, MultipartFile file) throws JsonMappingException, JsonProcessingException{
		System.out.println(sousCatDto+"zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
		SousCategoryDto sousCategoryDto = new ObjectMapper().readValue(sousCatDto, SousCategoryDto.class);
		ResponseEntity<SousCategory> sousCategory1 = getByLibelleAndCompanyId(sousCategoryDto.getLibelle(),company.getId());
	if(sousCategory1 != null)  {
		throw new RecordIsAlreadyExist("is already exist");
	}
	
	SousCategory sousCategory = sousCategoryMapper.mapToEntity(sousCategoryDto);
	if(file != null) {

		String newFileName = imageService.insertImag(file,company.getUser().getUsername(), "souscategory");
		sousCategory.setImage(newFileName);
	}
	sousCategory.setCompany(company);
	super.insert(sousCategory);
	return new ResponseEntity<SousCategoryDto>(HttpStatus.ACCEPTED);
	
	}

	public void deleteSousCategoryById(Long id, Company company) {
		Optional<SousCategory> sousCategory = getByIdAndCompanyId(id,company.getId());
		if(sousCategory.isEmpty()) {
			throw new RecordNotFoundException("This SousCategory with id: "+id+" Does Not Exist");
		}
	 super.deleteById(id,company.getId());
	
	}
	
}

package com.meta.store.werehouse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
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
	
	
	public ResponseEntity<SousCategoryDto> upDateSousCategory( SousCategoryDto sousCategoryDto, Company company) {
		Optional<SousCategory> sousCategory = sousCategoryRepository.findByIdAndCompanyId(sousCategoryDto.getId(),company.getId());
		if(sousCategory.isPresent()) {
			if(sousCategoryDto.getCategory() != sousCategory.get().getCategory()) {
				ResponseEntity<Category> category = categoryService.getById(sousCategoryDto.getCategory().getId());
			}
			SousCategory categ = sousCategoryMapper.mapToEntity(sousCategoryDto);
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
	
}

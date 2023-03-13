package com.meta.store.werehouse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.ArticleMapper;
import com.meta.store.werehouse.repository.ArticleRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService extends BaseService<Article, Long> {

	
	private final ArticleRepository articleRepository;
		
	private final ArticleMapper articleMapper; 
	
	
//	public List<Article> getAll(){
//		return articleRepository.findAll(Sort.by("id").ascending());
//	}
	
	public List<Article> findByNameContaining(String name,Long companyId) {
		return articleRepository.findAllByLibelleAndCompanyIdContaining(name,companyId);
	}
	
	public ResponseEntity<ArticleDto> upDateArticle( ArticleDto articleDto, Company company) {
		Optional<Article> article = articleRepository.findByIdAndCompanyId(articleDto.getId(),company.getId());
		if(article.isPresent()) {
			Article categ = articleMapper.mapToEntity(articleDto);
			categ.setCompany(company);
			articleRepository.save(categ);
			return ResponseEntity.ok(articleDto);
			
		}else {
			throw new RecordNotFoundException("Article Not Found");
		}
	}

	public Optional<Article> getByLibelle(String libelle, Long companyId) {
		return articleRepository.findByLibelleAndCompanyId(libelle, companyId);
	}

	public List<Article> getAllByCompanyId(Long companyId) {
		return articleRepository.findAllByCompanyId(companyId);
	}

	public ResponseEntity<Article> getByLibelleAndCompanyId(String name, Long companyId) {
		Optional<Article> categ = articleRepository.findByLibelleAndCompanyId(name,companyId);
		if(!categ.isEmpty()) {
		Article article = categ.get();
		return ResponseEntity.ok(article);
		}
		else return null;
	}
	
	public Optional<Article> getByIdAndCompanyId(Long id , Long companyId) {
		return articleRepository.findByIdAndCompanyId(id, companyId);
	}
	
	
}

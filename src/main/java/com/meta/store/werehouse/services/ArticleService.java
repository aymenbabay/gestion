package com.meta.store.werehouse.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Fournisseur;
import com.meta.store.werehouse.entity.Inventory;
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
	
	private final InventoryService inventoryService;

	private final ImageService imageService;

	
	
//	public List<Article> getAll(){
//		return articleRepository.findAll(Sort.by("id").ascending());
//	}
	

	@CacheEvict(value = "article", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public ResponseEntity<ArticleDto> upDateArticle( MultipartFile file, String article, Company company) throws JsonMappingException, JsonProcessingException {
		
		ArticleDto articleDto = new ObjectMapper().readValue(article, ArticleDto.class);
		if(file != null) {
			
			String newFileName = imageService.insertImag(file,company.getUser().getUsername(), "article");
			articleDto.setImage(newFileName);
				}
		Optional<Article> article1 = articleRepository.findByIdAndCompanyId(articleDto.getId(),company.getId());
		if(article1.isEmpty()) {
			throw new RecordNotFoundException("Article Not Found");
		}
		Article art = articleMapper.mapToEntity(articleDto);
			art.setCompany(company);
//			if(articleDto.getProvider() != null) {
//				for(Fournisseur f : article.get().getProviders()) {
//					if( !f.getCode().equals(articleDto.getProvider().getCode())){
//						Set<Fournisseur> fournisseurs = new HashSet<>();
//						fournisseurs.add(articleDto.getProvider());
//						art.setProviders(fournisseurs);
//					}
//				}
//			}
			double deference = article1.get().getQuantity()-articleDto.getQuantity();
			
				inventoryService.updateArticle(art,company,deference);				
			
			articleRepository.save(art);
			return ResponseEntity.ok(articleDto);
			
		
	}


	// i use it in command Line service
	@Cacheable(value = "article", key = "#root.methodName + '_' + #company.id + '_' + #code_article")
	public Article findByCodeAndCompanyId(String code_article, Company company) {
		Optional<Article> article = articleRepository.findByCodeAndCompanyId(code_article,company.getId());
		if(article.isEmpty()) {
			throw new RecordNotFoundException("Article with code: "+code_article+" Not Found");
		}
		return article.get();
	}

	@CacheEvict(value = "article", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public ResponseEntity<ArticleDto> insertArticle( MultipartFile file, String article3, Company company) throws JsonMappingException, JsonProcessingException {
		ArticleDto articleDto = new ObjectMapper().readValue(article3, ArticleDto.class);
		if(file != null) {
			
			String newFileName = imageService.insertImag(file,company.getUser().getUsername(), "article");
			articleDto.setImage(newFileName);
				}
		Optional<Article> article1 = articleRepository.findByLibelleAndCompanyId(articleDto.getLibelle(),company.getId());
		Optional<Article> article2 = articleRepository.findByCodeAndCompanyId(articleDto.getCode(),company.getId());
		
		if(article1.isPresent() && article2.isPresent())  {
			throw new RecordIsAlreadyExist("Article Is Already Exist");
		}
		if(article1.isPresent()) {
			throw new RecordIsAlreadyExist("Article libelle Is Already Exist");
		}
		if(article2.isPresent()) {
			throw new RecordIsAlreadyExist("Article code Is Already Exist");
		}
		Article article = articleMapper.mapToEntity(articleDto);
		article.setCompany(company);
//		if(articleDto.getProvider() != null) {
//		Set<Fournisseur> fournisseurs = new HashSet<>();
//			fournisseurs.add(articleDto.getProvider());
//			article.setProviders(fournisseurs);
//		}
		super.insert(article);
		inventoryService.makeInventory(article, company);
		return new ResponseEntity<ArticleDto>(HttpStatus.ACCEPTED);
		
	}

	@CacheEvict(value = "article", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public void addQuantity(Long id, Long quantity, Long companyId) {
		Optional<Article> article = articleRepository.findByIdAndCompanyId(id,companyId);
		if(article.isEmpty()) {
			throw new RecordNotFoundException("there is no article with id: "+id);
		}
		article.get().setQuantity(article.get().getQuantity()+quantity);
		articleRepository.save(article.get());
		inventoryService.addQuantity(article.get(), companyId,quantity);

	}

	@CacheEvict(value = "article", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public ResponseEntity<String> deleteByIdAndCompanyId(Long id, Long companyId) {
		Optional<Article> article = articleRepository.findByIdAndCompanyId(id, companyId);
			if(article.isEmpty()) {
				throw new RecordNotFoundException("This Article Does Not Exist");
			}
				articleRepository.deleteByIdAndCompanyId(id,companyId);
				inventoryService.deleteByArticleCode(article.get().getCode(), companyId);
			return ResponseEntity.ok("successfuly deleted");
	}

	public List<ArticleDto> getByNameContaining(String articlenamecontaining, Company company) {
		List<Article> article = new ArrayList<>();
		article = articleRepository.findAllByLibelleAndCompanyIdContaining(articlenamecontaining,company.getId());
		if(!article.isEmpty()) {
	List<ArticleDto> articleDto = new ArrayList<>();
	for(Article i : article) {
			ArticleDto artDto =  articleMapper.mapToDto(i);
			articleDto.add(artDto);
	}
	return articleDto;}
		throw new RecordNotFoundException("there is no record cointaining "+articlenamecontaining);

	}

	@Cacheable(value = "article", key = "#root.methodName + '_' + #company.id")
	public List<ArticleDto> getArticleByCompany(Company company) {
		System.out.println("article service 1aaaaaaaaaaaaaaaaaaaaaaaaaa");
		List<Article> articles = articleRepository.findAllByCompanyId(company.getId());
		if(articles.isEmpty()) {
			throw new RecordNotFoundException("there is no article");
		}
		List<ArticleDto> articlesDto = new ArrayList<>();
		System.out.println("article service 222222222222222222222222222222222222222222222");
		for(Article i : articles) {
			ArticleDto articleDto = articleMapper.mapToDto(i);
			articlesDto.add(articleDto);
		}
		return articlesDto;
	}

	@Cacheable(value = "article", key = "#root.methodName + '_' + #company.id")
	public ResponseEntity<ArticleDto> getArticleById(String name, Long id) {

		Optional<Article> article = articleRepository.findByLibelleAndCompanyId(name,id);
		if(article.isEmpty()) {
			 throw new RecordNotFoundException("There Is No Article With Libelle : "+name);
		}
		ArticleDto dto = articleMapper.mapToDto(article.get());
		return ResponseEntity.ok(dto);
	}


	public List<ArticleDto> getdgdgeg() {
		List<Article> article = articleRepository.findRandomArticles();
		if(article== null) {
			throw new RecordNotFoundException("No Article");
		}
			List<ArticleDto> articlesDto = new ArrayList<>();
			for(Article i:article) {
			ArticleDto dto = articleMapper.mapToDto(i);
			articlesDto.add(dto);
	}
			return articlesDto;
	}
	
	
	
}

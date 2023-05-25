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
import org.springframework.web.bind.annotation.RequestBody;
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
import com.meta.store.werehouse.dto.CompanyArticleDto;
import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.CompanyArticle;
import com.meta.store.werehouse.entity.Fournisseur;
import com.meta.store.werehouse.entity.Inventory;
import com.meta.store.werehouse.mapper.ArticleMapper;
import com.meta.store.werehouse.mapper.CompanyArticleMapper;
import com.meta.store.werehouse.repository.ArticleRepository;
import com.meta.store.werehouse.repository.CompanyArticleRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService extends BaseService<Article, Long> {

	
	private final ArticleRepository articleRepository;
	
	private final CompanyArticleRepository companyArticleRepository;
		
	private final ArticleMapper articleMapper; 
	
	private final CompanyArticleMapper companyArticleMapper;
	
	private final InventoryService inventoryService;

	private final ImageService imageService;

	private final CompanyService companyService;
	
	private final FournisseurService providerService;
	
//	public List<Article> getAll(){
//		return articleRepository.findAll(Sort.by("id").ascending());
//	}
	

	@CacheEvict(value = "article", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public ResponseEntity<ArticleDto> upDateArticle( MultipartFile file, String article, Company company) throws JsonMappingException, JsonProcessingException {
		ArticleDto articleDto = new ObjectMapper().readValue(article, ArticleDto.class);
		if(articleDto.getFournisseur() !=null) {
			
		}
		if(file != null) {
			
			String newFileName = imageService.insertImag(file,company.getUser().getUsername(), "article");
			articleDto.setImage(newFileName);
				}
		Optional<Article> article1 = articleRepository.findByIdAndCompanyId(articleDto.getId(),company.getId());
		if(article1.isEmpty()) {
			throw new RecordNotFoundException("Article Not Found");
		}
		Article art = articleMapper.mapToEntity(articleDto);
		Set<Company> companies = new HashSet<>();
		companies.add(company);
		art.setCompanies(companies);
			if(articleDto.getFournisseur() != null) {
				// check if provider is my own provider or an exist provider by userId then 
				//1- if my own provider : make a relation ship between provider and article
				//2-
				boolean existArticle = articleRepository.existsByCodeAndFournisseurId( articleDto.getCode(),articleDto.getFournisseur().getId());
				if(!existArticle) {
					throw new RecordNotFoundException("this fournisseur: "+articleDto.getFournisseur()+" don't have this article"+articleDto.getLibelle());
				}
			boolean existProvider = companyService.hasProvider(articleDto.getFournisseur().getId());
			if(!existProvider) {
				
//				for(Fournisseur f : articleDto.getFournisseur()) {
//					if( !f.getCode().equals(articleDto.getFournisseur().getCode())){
//						Set<Fournisseur> fournisseurs = new HashSet<>();
//						fournisseurs.add(articleDto.getFournisseur());
//						art.setProviders(fournisseurs);
//					}
//				}
			}
			}
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
		System.out.println("article service insert article first of function");
		ArticleDto articleDto = new ObjectMapper().readValue(article3, ArticleDto.class);
		System.out.println("article service insert article apres Object mapper");
		if(file != null) {
			System.out.println("article service insert article file not null");
			
			String newFileName = imageService.insertImag(file,company.getUser().getUsername(), "article");
			articleDto.setImage(newFileName);
				}
		System.out.println("article service insert article out of insert img");
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
		System.out.println("article service insert article befor article mapper");
		Article article = articleMapper.mapToEntity(articleDto);
		Set<Company> companies = new HashSet<>();
		companies.add(company);
		System.out.println("article service insert article apres add companies to company");
		article.setCompanies(companies);
		System.out.println("article service insert article apres set companies  ");
		if(articleDto.getFournisseur() == null) {
			Optional<Fournisseur> fournisseur = providerService.getMe(company.getUser().getId());
			article.setFournisseur(fournisseur.get());
		}else {
			boolean exist = providerService.existsById(articleDto.getFournisseur().getId());
			if(exist) {				
				article.setFournisseur(articleDto.getFournisseur());
			}	
		}
		System.out.println("article service insert article + the first element "+article.getCompanies().iterator().next().getId()+"article id"+article.getId());
		super.insert(article);
		inventoryService.makeInventory(article, company);
		return new ResponseEntity<ArticleDto>(HttpStatus.ACCEPTED);
		
	}


	@CacheEvict(value = "article", key = "#root.methodName + '_' + #company.id", allEntries = true)
	public void insertExistArticle(CompanyArticleDto companyArticleDto, Company company ) {
		Long idProvider = companyArticleDto.getArticle().getFournisseur().getId();
		boolean existRelation = providerService.existRelationBetweenProviderAndCompany(idProvider,company.getId());
		String codeArticle = companyArticleDto.getArticle().getCode();
		boolean existArticle = articleRepository.existsByCodeAndFournisseurId(codeArticle, idProvider);
		if(!existArticle) {
			throw new RecordNotFoundException("this provider do not has this article "+codeArticle);
		}
		
		CompanyArticle companyArticle = companyArticleMapper.mapToEntity(companyArticleDto);
		companyArticleRepository.save(companyArticle);
		
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
			//	articleRepository.deleteByIdAndCompanyId(id,companyId);
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

	//@Cacheable(value = "article", key = "#root.methodName + '_' + #company.id")
	public List<ArticleDto> getArticleByCompany(Company company) {
		System.out.println("article service get article by ciompany ");
		List<Article> articles = articleRepository.findAllByCompanyId(company.getId());
		if(articles.isEmpty()) {
			System.out.println("article service get article by ciompany if article is empty");
			throw new RecordNotFoundException("there is no article");
		}
		System.out.println("article service get article by ciompany if article not empty");
		List<ArticleDto> articlesDto = new ArrayList<>();
		for(Article i : articles) {
			ArticleDto articleDto = articleMapper.mapToDto(i);
			System.out.println("article service get article by ciompany "+articleDto.getCode());
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


	public List<ArticleDto> getAllProvidersArticleByProviderId(Company company, Long id) {
		boolean existProvider = providerService.existsById(id);
		List<ArticleDto> articlesDto = new ArrayList<ArticleDto>();
		if(existProvider) {
			boolean existMyProvider = providerService.existsMyProvider(company.getId(),id);
			if(existMyProvider) {
				List<Article> articles = articleRepository.findAllByFournisseurId(id);
				if(articles.isEmpty()) {
					throw new RecordNotFoundException("this Provider Don't have any article yet");
				}
				for(Article i : articles) {
					ArticleDto articleDto = articleMapper.mapToDto(i);
					articlesDto.add(articleDto);
				}
			}
		}
		return articlesDto;
	}


//	@Cacheable(value = "article", key = "#root.methodName + '_' + #company.id")
//	public List<ArticleDto> getAllMyVirtual(Company company) {
//		List<Article> articles = articleRepository.findAllMyVirtual(company.getId());
//		List<ArticleDto> articlesDto = new ArrayList<>();
//		for(Article i : articles) {
//			ArticleDto articleDto = articleMapper.mapToDto(i);
//			articlesDto.add(articleDto);
//		}
//		return articlesDto;
//	}
	
	
	
}

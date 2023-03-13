package com.meta.store.werehouse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.meta.store.werehouse.mapper.ArticleMapper;
import com.meta.store.werehouse.services.ArticleService;
import com.meta.store.werehouse.services.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/article")
@RequiredArgsConstructor
public class ArticleController {

	
	private final ArticleService articleService;
	
	private final ArticleMapper articleMapper;
	
	private final BaseService<Article, Long> baseService;
	
	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	
	@GetMapping("/{articlenamecontaining}")
	public List<ArticleDto> getByNameContaining(@PathVariable String articlenamecontaining ){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		List<Article> article = new ArrayList<>();
			article =	articleService.findByNameContaining(articlenamecontaining,company.getId());
			if(!article.isEmpty()) {
		List<ArticleDto> articleDto = new ArrayList<>();
		for(Article i : article) {
				ArticleDto artDto =  articleMapper.mapToDto(i);
				articleDto.add(artDto);
		}
		return articleDto;}
			throw new RecordNotFoundException("there is no record cointaining "+articlenamecontaining);
	}
	@GetMapping("/getbycompany")
	public ResponseEntity<List<ArticleDto>> getArticleByCompany(){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Long companyId = companyService.findCompanyIdByUserId(userId).getId();
		List<Article> articles = articleService.getAllByCompanyId(companyId);
		if(!articles.isEmpty()) {
		List<ArticleDto> articlesDto = new ArrayList<>();
		for(Article i : articles) {
			System.out.println(i.getCode());
			ArticleDto articleDto = articleMapper.mapToDto(i);
			articlesDto.add(articleDto);
		}
		return ResponseEntity.ok(articlesDto);}
		throw new RecordNotFoundException("there is no article");
	}
	
	@GetMapping("/l/{name}")
	public ResponseEntity<ArticleDto> getArticleById(@PathVariable String name){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		ResponseEntity<Article> article = articleService.getByLibelleAndCompanyId(name,company.getId());
		if(article != null) {
		ArticleDto dto = articleMapper.mapToDto(article.getBody());
		return ResponseEntity.ok(dto);
		}
		
		else throw new RecordNotFoundException("There Is No Article With Libelle : "+name);
		}
		else throw new RecordNotFoundException("You Have No Company Please Create One :) ");
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<ArticleDto> insertArticle(@RequestBody @Valid ArticleDto articleDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		Optional<Article> article1 = articleService.getByLibelle(articleDto.getLibelle(),company.getId());
		if(article1.isEmpty() || article1.isPresent() && !article1.get().getCompany().equals(company))  {
		Article article = articleMapper.mapToEntity(articleDto);
		article.setCompany(company);
		baseService.insert(article);
		return new ResponseEntity<ArticleDto>(HttpStatus.ACCEPTED);
		}else {
			throw new RecordIsAlreadyExist("is already exist");
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<ArticleDto> upDateArticle(@RequestBody @Valid ArticleDto articleDto){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
		return articleService.upDateArticle(articleDto,company);
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
	@DeleteMapping("/{id}")
	public void deleteArticleById(@PathVariable Long id){
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			Optional<Article> article = articleService.getByIdAndCompanyId(id,company.getId());
			if(article.isPresent()) {
		 baseService.deleteById(id,company.getId());
			}else
			throw new RecordNotFoundException("This Article Does Not Exist");
		}else throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
	}
	
}

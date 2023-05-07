package com.meta.store.werehouse.controller;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.aspectj.apache.bcel.classfile.Module.Require;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meta.store.base.error.RecordNotFoundException;
import com.meta.store.base.security.config.JwtAuthenticationFilter;
import com.meta.store.base.security.service.AppUserService;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.dto.CompanyDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.mapper.ArticleMapper;
import com.meta.store.werehouse.services.ArticleService;
import com.meta.store.werehouse.services.CompanyService;
import com.meta.store.werehouse.services.ImageService;
import com.meta.store.werehouse.services.WorkerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/article")
@RequiredArgsConstructor
public class ArticleController {

	
	private final ArticleService articleService;

	private final JwtAuthenticationFilter authenticationFilter;
	
	private final AppUserService appUserService;
	
	private final CompanyService companyService;
	
	private final WorkerService workerService;
	
	@GetMapping("/{articlenamecontaining}")
	public List<ArticleDto> getByNameContaining(@PathVariable String articlenamecontaining ){
		Company company = getCompany();
		return articleService.getByNameContaining(articlenamecontaining,company);
	}
	
	@GetMapping("/{id}/{quantity}")
	public void addQuantity(@PathVariable Long quantity, @PathVariable Long id) {
		Long companyId = getCompany().getId();
		articleService.addQuantity(id,quantity,companyId);
	}
	
	@GetMapping("/getbycompany/{id}")
	public List<ArticleDto> getArticleByCompany(@PathVariable Long id){
		System.out.println(id);
		if(id !=0) {
			ResponseEntity<Company> company = companyService.getById(id);
			return articleService.getArticleByCompany(company.getBody());
		}
		Company company = getCompany();
		return articleService.getArticleByCompany(company);
	}
	
	@GetMapping("/l/{name}")
	public ResponseEntity<ArticleDto> getArticleById(@PathVariable String name){
		Long companyId = getCompany().getId();
		return articleService.getArticleById(name,companyId);
	}
	
	@PostMapping("/add")
	public ResponseEntity<ArticleDto> insertArticle(
			 @RequestParam(value ="file", required = false) MultipartFile file,
			 @RequestParam("article") String article)
			throws Exception{
		Company company = getCompany();
		return articleService.insertArticle(file,article,company, authenticationFilter.userName);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ArticleDto> upDateArticle(
			 @RequestParam(value ="file", required = false) MultipartFile file,
			 @RequestParam("article") String article) throws Exception{
		Company company = getCompany();
		return articleService.upDateArticle(file,article, company, authenticationFilter.userName);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String> deleteArticleById(@PathVariable Long id){
		Long companyId = getCompany().getId();
		return articleService.deleteByIdAndCompanyId(id,companyId);
	}
	
	@GetMapping("/getrandom")
	public List<ArticleDto> getaertt(){
		return articleService.getdgdgeg();
	}
	private Company getCompany() {
		Long userId = appUserService.findByUserName(authenticationFilter.userName).getId();
		Company company = companyService.findCompanyIdByUserId(userId);
		if(company != null) {
			return company;
		}
		Long companyId = workerService.getCompanyIdByUserName(authenticationFilter.userName);
		if(companyId != null) {			
		ResponseEntity<Company> company2 = companyService.getById(companyId);
		return company2.getBody();
		}
			throw new RecordNotFoundException("You Dont Have A Company Please Create One If You Need ");
			
	}
	
	
}

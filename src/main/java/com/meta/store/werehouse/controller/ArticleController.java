package com.meta.store.werehouse.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.services.ArticleService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/werehouse/article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private BaseService<Article, Long> baseService;
	
	@GetMapping("/{con}")
	public List<Article> getByNameContaining(@PathVariable String con ){
		return articleService.findByNameContaining(con);
	}
	
	@GetMapping("/get")
	public List<Article> getAll(){
		return baseService.getAll();
	}
	
	@GetMapping("/ar/{id}")
	public Optional<Article> getArticleById(@PathVariable Long id){
		return baseService.getById(id);
	}
	
	@PostMapping("/add")
	public ResponseEntity<Article> insertArticle(@RequestBody @Valid Article article){
		baseService.insert(article);
		return new ResponseEntity<Article>(HttpStatus.ACCEPTED);
	}
}

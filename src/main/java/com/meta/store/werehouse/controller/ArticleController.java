package com.meta.store.werehouse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.dto.ArticleDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.mapper.ArticleMapper;
import com.meta.store.werehouse.services.ArticleService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/werehouse/article")
@RequiredArgsConstructor
public class ArticleController {

	
	private final ArticleService articleService;
	
	private final ArticleMapper articleMapper;
	
	private final BaseService<Article, Long> baseService;
	
	@GetMapping("/{con}")
	public List<ArticleDto> getByNameContaining(@PathVariable String con ){
		List<Article> article = new ArrayList<>();
			article =	articleService.findByNameContaining(con);
		List<ArticleDto> articleDto = new ArrayList<>();
		for(Article i : article) {

				ArticleDto artDto =  articleMapper.mapToDto(i);
				articleDto.add(artDto);
		

		}
		return articleDto;
	}
	
	@GetMapping("/get")
	public List<Article> getAll(){
		return baseService.getAll();
	}
	
	@GetMapping("/ar/{id}")
	public ResponseEntity<ArticleDto> getArticleById(@PathVariable Long id){
		ResponseEntity<Article> article = baseService.getById(id);
		ArticleDto dto = articleMapper.mapToDto(article.getBody());
		return ResponseEntity.ok(dto);
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<Article> insertArticle(@RequestBody @Valid Article article){
		baseService.insert(article);
		return new ResponseEntity<Article>(HttpStatus.ACCEPTED);
	}
}

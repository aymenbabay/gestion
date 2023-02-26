package com.meta.store.werehouse.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.meta.store.base.service.BaseService;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.repository.ArticleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArticleService extends BaseService<Article, Long> {

	@Autowired
	private ArticleRepository articleRepository;
	
//	public List<Article> getAll(){
//		return articleRepository.findAll(Sort.by("id").ascending());
//	}
	
	public List<Article> findByNameContaining(String name) {
		return articleRepository.findAllByNameContaining(name);
	}
	
	
	@Override
	public ResponseEntity<Article> insert(Article article) {
		// TODO Auto-generated method stub
		return super.insert(article);
	}
}

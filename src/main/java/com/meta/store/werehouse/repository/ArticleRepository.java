package com.meta.store.werehouse.repository;

import java.util.List;
import com.meta.store.base.repository.BaseRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.meta.store.werehouse.entity.Article;


public interface ArticleRepository extends BaseRepository<Article,Long> {

	List<Article> findAllByNameContaining(String name);

	@Query(value = "select art from Article art where art.code = :art")
	Optional<Article> findByCode(String art);


//	@Query(value = "select art from Article art join art.department dept where dept.id = :deptId")
//	Optional<Article> findByName(Long deptId);
}

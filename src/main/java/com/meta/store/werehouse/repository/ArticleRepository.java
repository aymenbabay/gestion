package com.meta.store.werehouse.repository;

import java.util.List;
import com.meta.store.base.repository.BaseRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.werehouse.entity.Article;


public interface ArticleRepository extends BaseRepository<Article,Long> {


	List<Article> findAllByLibelleAndCompanyIdContaining(String libelle, Long companyId);

	@Query(value = "select art from Article art where art.code = :art")
	Optional<Article> findByCode(String art);

	@Query("SELECT a FROM Article a WHERE a.libelle = :libelle AND a.company.id = :companyId")
	Optional<Article> findByLibelleAndCompanyId(String libelle,Long companyId);


	@Query("SELECT a FROM Article a WHERE a.company.id = :companyId")
	List<Article> findByCompanyId(@Param("companyId") Long companyId);

	Optional<Article> findByIdAndCompanyId(Long id, Long id2);

	List<Article> findAllByCompanyId(Long companyId);

	Optional<Article> findByCodeAndCompanyId(String code_article, Long companyId);

	void deleteByIdAndCompanyId(Long id, Long companyId);

//	@Query(value = "SELECT * FROM article WHERE company_id = (SELECT id FROM company ORDER BY RAND() LIMIT 1) ORDER BY RAND() LIMIT 1", nativeQuery = true)
//	Optional<Article> findRandomArticleByRandomCompany();
	@Query(value = "SELECT a FROM Article a JOIN FETCH a.company c ORDER BY random() LIMIT 10")
    List<Article> findRandomArticles();




}
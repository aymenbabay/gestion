package com.meta.store.werehouse.repository;

import java.util.List;
import com.meta.store.base.repository.BaseRepository;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.meta.store.werehouse.entity.Article;


public interface ArticleRepository extends BaseRepository<Article,Long> {


	@Query("SELECT a FROM Article a JOIN a.companies c WHERE c.id = :companyId AND a.libelle = :libelle")
	List<Article> findAllByLibelleAndCompanyIdContaining(String libelle, Long companyId);

	@Query(value = "select art from Article art where art.code = :art")
	Optional<Article> findByCode(String art);

	@Query("SELECT a FROM Article a JOIN a.companies c WHERE a.libelle = :libelle AND c.id = :companyId")
	Optional<Article> findByLibelleAndCompanyId(String libelle,Long companyId);


	@Query("SELECT a FROM Article a JOIN a.companies c WHERE c.id = :companyId")
	List<Article> findByCompanyId( Long companyId);

	@Query("SELECT a FROM Article a JOIN a.companies c WHERE c.id = :id2 AND a.id = :id")
	Optional<Article> findByIdAndCompanyId(Long id, Long id2);

	@Query("SELECT a FROM Article a JOIN a.companies c WHERE c.id = :companyId")
	List<Article> findAllByCompanyId(Long companyId);

	@Query("SELECT a FROM Article a JOIN a.companies c WHERE c.id = :companyId AND a.code = :code_article")
	Optional<Article> findByCodeAndCompanyId(String code_article, Long companyId);


	@Query(value = "SELECT a FROM Article a JOIN FETCH a.companies c ORDER BY random() LIMIT 10")
    List<Article> findRandomArticles();

	 boolean existsByCodeAndFournisseurId(String code, Long fournisseurId);
	
	List<Article> findAllByFournisseurId(Long fournisseurId);





}
package com.meta.store.werehouse.repository;

import java.util.Optional;

import com.meta.store.base.repository.BaseRepository;
import com.meta.store.werehouse.entity.CompanyArticle;

public interface CompanyArticleRepository extends BaseRepository<CompanyArticle, Long>{

	Optional<CompanyArticle> findByArticleId(Long articleId);

}

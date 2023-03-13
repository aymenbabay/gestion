package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.util.List;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Category;
import com.meta.store.werehouse.entity.Company;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SousCategoryDto extends BaseDto<Long> implements Serializable {

	@NotBlank(message = "Code Field Must Not Be Empty")
	private String code;

	@NotBlank(message = "Libelle Field Must Not Be Empty")	
	private String libelle;
	
	
	private Category category;
	
	private Company company;
}

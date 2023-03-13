package com.meta.store.werehouse.dto;

import java.io.Serializable;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.werehouse.entity.Invoice;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandLineDto extends BaseDto<Long> implements Serializable{

	private String code_article;
	
	private String libelle_article;
	
	private Double quantity;
	
	private String unit;

	private Double tva;
	
	private Double prix_article_unit;

	
	private Invoice invoice;
}

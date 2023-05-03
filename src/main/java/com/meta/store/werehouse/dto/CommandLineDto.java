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

	private String codeArticle;
	
	private String libelleArticle;
	
	private Double quantity;
	
	private String unit;

	private Double tva;
	
	private Double prixArticleUnit;

	private Invoice invoice;

	private Double totTva;

	private Double prixArticleTot;
}

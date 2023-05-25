package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.util.Set;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Category;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Fournisseur;
import com.meta.store.werehouse.entity.SousCategory;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyArticleDto extends BaseDto<Long> implements Serializable {

	
	private String discription;
	
	private Double cost;
	
	@Positive(message = "Quantity Field Must Be A Positive Number")
	private Double quantity;
	
	private Double minQuantity;
	
	private Double tva;
	
	@Positive(message = "Selling_Price Field Must Be A Positive Number")
	private Double sellingPrice;
	
	
    private Article article;

   
    private Company company;

}

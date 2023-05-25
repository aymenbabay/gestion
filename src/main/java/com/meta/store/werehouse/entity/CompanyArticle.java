package com.meta.store.werehouse.entity;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="company_article")
public class CompanyArticle extends BaseEntity<Long> {

	
	private String discription;
	
	private Double cost;
	
	//@Positive(message = "Quantity Field Must Be A Positive Number")
	private Double quantity;
	
	private Double minQuantity;
	
	private Double tva;
	
	//@Positive(message = "Selling_Price Field Must Be A Positive Number")
	private Double sellingPrice;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}

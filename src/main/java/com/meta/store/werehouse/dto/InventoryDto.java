package com.meta.store.werehouse.dto;

import java.io.Serializable;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.werehouse.entity.Company;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InventoryDto extends BaseDto<Long> implements Serializable {
	
private Double current_quantity;
	
	private Double out_quantity;
	
	private Double in_quantity; 
	
	private String libelle_article;
	
	private String articleCode;
	
	private String bestClient;
	
	private Double articleCost;
	
	private Double articleSelling;
	
	private Company company;
	
}

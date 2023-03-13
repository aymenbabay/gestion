package com.meta.store.werehouse.entity;

import java.util.List;


import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="inventory_werehouse")
public class Inventory extends BaseEntity<Long> {

	
	private Double current_quantity;
	
	private Double out_quantity;
	
	private Double in_quantity; 
	
	private String libelle_article;
	
	private Long id_article;
	
	private String bestClient;
	
	private Double articleCost;
	
	private Double articleSelling;
	

	@OneToOne()
	@JoinColumn(name = "company_id",referencedColumnName = "id")
	private Company company;
	
}

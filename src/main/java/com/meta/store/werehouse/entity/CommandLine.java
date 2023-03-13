package com.meta.store.werehouse.entity;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="were_house_line_command")
public class CommandLine extends BaseEntity<Long>{


	private String code_article;
	
	private String libelle_article;
	
	private Double quantity;
	
	private String unit;

	private Double tva;
	
	private Double prix_article_unit;

	@ManyToOne
	@JoinColumn(name = "clientId")
	private Invoice invoice;
}

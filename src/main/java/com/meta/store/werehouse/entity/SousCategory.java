package com.meta.store.werehouse.entity;

import java.util.List;
import java.util.UUID;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="sous_category_werehouse")
public class SousCategory extends BaseEntity<Long> {

	@NotBlank(message = "Code Field Must Not Be Empty")
	private String code;

	@NotBlank(message = "Libelle Field Must Not Be Empty")	
	private String libelle;
	
	private String image;
	
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;
}

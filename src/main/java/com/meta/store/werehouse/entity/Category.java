package com.meta.store.werehouse.entity;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
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
@Table(name="category_werehouse")
public class Category extends BaseEntity<Long>{

	@NotBlank(message = "Code Field must not be Empty")
	private String code;
	
	private String libelle;
}

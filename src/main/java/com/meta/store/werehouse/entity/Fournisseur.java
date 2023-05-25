package com.meta.store.werehouse.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.meta.store.base.Entity.BaseEntity;
import com.meta.store.base.security.entity.AppUser;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="fournisseur_werehouse")
public class Fournisseur extends BaseEntity<Long> implements Serializable{

	@NotBlank(message = "Supplier Name Field Must Not Be Empty")
	private String name;

	@NotBlank(message = "Supplier Code Field Must Not Be Empty")
	private String code;
	
	private String nature;
	
	private Double credit;
	
	@PositiveOrZero(message = "Supplier Mouvement Field Must Be Positive or Zero ")
	private Double mvt;
	
	private String phone;
	
	private String address;
	
	@Email
	private String email;
	
	private String matfisc;
	
	@ManyToMany
	@JoinTable(name = "company_fournisseur", joinColumns = @JoinColumn(name="company_id"), 
				inverseJoinColumns = @JoinColumn(name="fournisseur_id"))
	private Set<Company> companies = new HashSet<>();
	

	@OneToOne()
	@JoinColumn(name = "user_id")
	private AppUser user;
}

package com.meta.store.werehouse.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.meta.store.base.Entity.BaseEntity;
import com.meta.store.base.security.entity.AppUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Company extends BaseEntity<Long> implements Serializable{

	@NotBlank(message = "Company Name Must Be Not Empty")
	private String name;
	
	private String identityNumber;
	
	private String nationalIdNumber;
	
	private String address;
	
	private String indestrySector;
	
	private String capital;
	
	private String logo;
	
	private int workForce;
	
	private String legalStructure;
	
	private String taxStatus;
	
	private String phone;
	
	private String email;
	
	@OneToOne()
	@JoinColumn(name = "user_id")
	private AppUser user;
	


	
}














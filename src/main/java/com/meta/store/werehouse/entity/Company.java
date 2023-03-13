package com.meta.store.werehouse.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Company extends BaseEntity<Long>{

	private String companyName;
	
	private String identityNumber;
	
	private String National_id_number;
	
	private String address;
	
	private String indestrySector;
	
	private String capital;
	
	private String logo;
	
	private String workForce;
	
	private String legalStructure;
	
	private String taxStatus;
	
	@OneToOne()
	@JoinColumn(name = "user_id")
	private AppUser user;
	


	
}














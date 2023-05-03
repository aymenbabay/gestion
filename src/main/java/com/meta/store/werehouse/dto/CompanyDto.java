package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Category;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Fournisseur;
import com.meta.store.werehouse.entity.Inventory;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.entity.Order;
import com.meta.store.werehouse.entity.Shipment;
import com.meta.store.werehouse.entity.SousCategory;
import com.meta.store.werehouse.entity.Worker;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CompanyDto extends BaseDto<Long> implements Serializable{
	
	@NotBlank(message = "CompanyName Must Be Not Empty")
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
	
	private AppUser user;
	
	//private Set<Client> clients = new HashSet<>();

	//private Set<Fournisseur> fournisseurs = new HashSet<>();
	
}

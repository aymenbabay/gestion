package com.meta.store.werehouse.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
@Table(name="article_werehouse")
public class Article extends BaseEntity<Long> implements Serializable{

	
	@NotBlank(message = "Libelle Field Must Not Be Empty")
	private String libelle;
	
	@NotBlank(message = "Code Field Must Not Be Empty")
	@Column(unique = false)
	private String code;

	private String unit;
	
	private String discription;
	
	@Positive(message = "Cost Field Must Be A Positive Number")
	private Double cost;

	@Positive(message = "Quantity Field Must Be A Positive Number")
	private Double quantity;
	
	private Double minQuantity;
	
	private Double maxQuantity;
	
	@Positive(message = "Selling_Price Field Must Be A Positive Number")
	private Double sellingPrice;
	
	private String barcode;
	
	//@NotBlank(message = "TVA Field Must Not Be Empty")
	private Double tva;
//	@NotNull(message = "يهديك مش فارغ")it's accept "" but @NotEmpty can't accept it and @NotBlank dosn't accept "" or null
//	private String name;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "categoryId")
	private Category category;
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "sousCategoryId")
	private SousCategory sousCategory;
	
	@ManyToOne()
	@JoinColumn(name = "companyId")
	private Company company;
	
	private String image;


	@Column(nullable = true)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "article_fournisseur",
	joinColumns = @JoinColumn(name="fournisseur_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="article_id",referencedColumnName = "id"))
	private Set<Fournisseur> providers = new HashSet<>();

	public Article(@NotBlank(message = "Libelle field must not be empty") String libelle,
			@NotBlank(message = "Code Field Must not Be Empty") String code, String ref, String discription, Double tva) {
		super();
		this.libelle = libelle;
		this.code = code;
		this.unit = ref;
		this.discription = discription;
		this.tva = tva;
	}
	
	
	

}
 







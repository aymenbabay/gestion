package com.meta.store.werehouse.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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
@Table(name="fournisseur_article")
public class Article extends BaseEntity<Long> implements Serializable{

	
	@NotBlank(message = "Libelle Field Must Not Be Empty")
	private String libelle;
	
	@NotBlank(message = "Code Field Must Not Be Empty")
	@Column(unique = true)
	private String code;

	private String unit;
	
	private String discription;
	
	@Positive(message = "Cost Field Must Be A Positive Number")
	private Double cost;

	@Positive(message = "Quantity Field Must Be A Positive Number")
	private Double quantity;
	
	private Double minQuantity;
	
	
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
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "company_articl",
				joinColumns = @JoinColumn(name="article_id"), 
				inverseJoinColumns = @JoinColumn(name="company_id"))//,referencedColumnName = "id"
	private Set<Company> companies = new HashSet<>();
	
	@ManyToOne()
	@JoinColumn(name = "fournisseurId")
	private Fournisseur fournisseur;
	
	private String image;


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
 







package com.meta.store.werehouse.entity;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class Article extends BaseEntity<Long> {

	
	@NotBlank(message = "Libelle field must not be empty")
	private String libelle;
	
	@NotBlank(message = "Code Field Must not Be Empty")
	private String code;

	private String ref;
	
	private String discription;
	
	private Double prix;
	
	@NotNull(message = "يهديك مش فارغ")//it's accept "" but @NotEmpty can't accept it and @NotBlank dosn't accept "" or null
	private String name;

	@ManyToOne
	@JoinColumn(name = "article_id")
	private Inventory inventory;
//	@ManyToOne
//	private Category category;

	public Article(@NotBlank(message = "Libelle field must not be empty") String libelle,
			@NotBlank(message = "Code Field Must not Be Empty") String code, String ref, String discription,
			@NotNull(message = "يهديك مش فارغ") String name) {
		super();
		this.libelle = libelle;
		this.code = code;
		this.ref = ref;
		this.discription = discription;
		this.name = name;
	}
	
	
	

}
 
package com.meta.store.werehouse.entity;

import java.util.HashSet;
import java.util.Set;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name="invoice_werehouse")
public class Invoice extends BaseEntity<Long>{

	public Invoice(long l, String string, double d) {
		// TODO Auto-generated constructor stub
	}

	private Long code;
	
	private String ref_article;
	
	private String libelle_article;
	
	private Double quantity;
	
	private String unit;

	private Double tva;
	
	private Double tot_tva;
	
	private Double tot_tva_invoice;
	
	private Double prix_article_unit;
	
	private Double prix_article_tot;
	
	private Double prix_invoice_tot;

	@ManyToMany
	@JoinTable(name = "client_invoice", joinColumns = @JoinColumn(name="InvoiceId"), inverseJoinColumns = @JoinColumn(name="ClientId"))
	private Set<Client> clients = new HashSet<>();
	
	
	
}

package com.meta.store.werehouse.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	

	private Long code;
		
	private Double tot_tva;
	
	private Double tot_tva_invoice;
		
	private Double prix_article_tot;
	
	private Double prix_invoice_tot;
	
	private LocalDateTime date_invoice;

	@ManyToOne
	@JoinColumn(name = "clientId")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;
	
	
	
	
}

package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InvoiceDto extends BaseDto<Long> implements Serializable {

	private Long code;
	
	private Double tot_tva;
	
	private Double tot_tva_invoice;
		
	private Double prix_article_tot;
	
	private Double prix_invoice_tot;
	
	private LocalDateTime date_invoice;


	private Client client;
	
	private Company company;
}

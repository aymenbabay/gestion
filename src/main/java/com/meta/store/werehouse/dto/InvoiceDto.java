package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class InvoiceDto extends BaseDto<Long> implements Serializable {

	private Long code;
	
	private Double tot_tva;
	
	private Double tot_tva_invoice;
		
	private Double prix_tot_article;
	
	private Double prix_invoice_tot;
	
	private Boolean status;
	
	private LocalDateTime createdDate;

	private LocalDateTime LastModifiedDate;
	
	private String LastModifiedBy;
	
	private String CreatedBy;

	private Client client;
	
	private Company company;
}

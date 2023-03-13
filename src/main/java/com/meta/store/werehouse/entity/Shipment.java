package com.meta.store.werehouse.entity;

import java.sql.Date;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="shipment_werehouse")
public class Shipment extends BaseEntity<Long> {
	
	private String code;

	private Date dateCommande;
	
	private Date dateDelivery;
	
	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;
}
 
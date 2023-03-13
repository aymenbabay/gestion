package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.sql.Date;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.werehouse.entity.Company;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ShipmentDto extends BaseDto<Long> implements Serializable {

	private String code;

	private Date dateCommande;
	
	private Date dateDelivery;
	
	private Company company;
}

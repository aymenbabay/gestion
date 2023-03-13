package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.werehouse.entity.Client;
import com.meta.store.werehouse.entity.Company;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class OrderDto extends BaseDto<Long> implements Serializable {

	@UniqueElements(message = "This Order Code Is Already Use")
	private Long code;
	
	@Positive(message = "Order Quantity Must Be More Than Zero")
	private Double quantity;

	private Double current_quantity;
	
	private Client client;
	
	private Company company;
}

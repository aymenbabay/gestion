package com.meta.store.werehouse.entity;

import java.util.List;

import org.hibernate.validator.constraints.UniqueElements;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
@Table(name="order_werehouse")
public class Order extends BaseEntity<Long> {

	@UniqueElements(message = "This Order Code Is Already Use")
	private String code;
	
	@Positive(message = "Order Quantity Must Be More Than Zero")
	private Double quantity;

	private Double current_quantity;
	
	@ManyToOne
	@JoinColumn(name = "clientId")
	private Client client;
	
	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;
}

package com.meta.store.werehouse.entity;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
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
@Table(name="order_werehouse")
public class Order extends BaseEntity<Long> {

	private String code;
	
	private Double quantity;

	private Double current_quantity;
}

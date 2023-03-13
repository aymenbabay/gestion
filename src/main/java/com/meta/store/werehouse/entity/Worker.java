package com.meta.store.werehouse.entity;

import org.hibernate.validator.constraints.UniqueElements;

import com.meta.store.base.Entity.BaseEntity;
import com.meta.store.base.security.entity.AppUser;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@Table(name="worker_werehouse")
public class Worker extends BaseEntity<Long> {
	
	//@UniqueElements(message = "Worker Name Must Be Unique")
	private String name;
	
	private String phone;
	
	private String email;
	
	private String address;
	
	private Double salary;
	
	@OneToOne
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	private AppUser user;

	@ManyToOne
	@JoinColumn(name = "companyId")
	private Company company;
}

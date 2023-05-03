package com.meta.store.werehouse.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.UniqueElements;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.werehouse.entity.Company;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class WorkerDto extends BaseDto<Long> implements Serializable {
	
	private String name;
//	
//	private String phone;
//	
//	private String email;
//	
//	private String address;
	
	private AppUser user;

//	private Company company;

	private Double salary;
	

}

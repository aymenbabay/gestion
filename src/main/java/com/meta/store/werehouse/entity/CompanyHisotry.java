package com.meta.store.werehouse.entity;

import java.time.LocalDateTime;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class CompanyHisotry extends BaseEntity<Long> {

	private String workername;
	
	private LocalDateTime workerin;

	private LocalDateTime workerout;
	
	private LocalDateTime datecreation;
	
	@OneToOne
	@JoinColumn(name = "companyId")
	private Company company;
}

package com.meta.store.werehouse.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.meta.store.base.Entity.BaseEntity;
import com.meta.store.base.security.entity.AppUser;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
@Table(name="vacation_werehouse")
public class Vacation extends BaseEntity<Long> implements Serializable{

		
	private long usedday;
	
	private long remainingday;
		
	private LocalDateTime year;
	
	private LocalDateTime startdate;
	
	private LocalDateTime enddate;
	
	
	@OneToOne()
	@JoinColumn(name = "worker_id",referencedColumnName = "id")
	private Worker worker;
}

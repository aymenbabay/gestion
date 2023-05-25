package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.werehouse.entity.Worker;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VacationDto extends BaseDto<Long> implements Serializable{

		
	private long usedday;
	
	private long remainingday;
		
	private int year;
	
	private Date startdate;
	
	private Date enddate;
		
	private Worker worker;
}

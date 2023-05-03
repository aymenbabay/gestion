package com.meta.store.werehouse.dto;

import java.io.Serializable;

import com.meta.store.base.Entity.BaseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto extends BaseDto<Long>  implements Serializable{


	private String name;

	private String phone;
	
	private String address;

	private String email;
	
}

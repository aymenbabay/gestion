package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.SousCategory;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class FournisseurDto extends BaseDto<Long> implements Serializable {

	@NotBlank(message = "Supplier Name Field Must Not Be Empty")
	private String name;

	@NotBlank(message = "Supplier Code Field Must Not Be Empty")
	private String code;
	
	private String nature;
	
	private Double credit;
	
	@PositiveOrZero(message = "Supplier Mouvement Field Must Be Positive or Zero ")
	private Double mvt;
	
	private String phone;
	
	private String address;
	
	@Email
	private String email;

	private AppUser user;
	
}

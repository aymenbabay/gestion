package com.meta.store.werehouse.dto;

import java.io.Serializable;
import java.util.List;

import com.meta.store.base.Entity.BaseDto;
import com.meta.store.base.security.entity.AppUser;
import com.meta.store.werehouse.entity.Article;
import com.meta.store.werehouse.entity.Invoice;
import com.meta.store.werehouse.entity.Order;
import com.meta.store.werehouse.entity.SousCategory;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ClientDto extends BaseDto<Long> implements Serializable{


	@NotBlank(message = "Client Name Field Must Not Be Empty")
	private String name;

	@NotBlank(message = "Client Code Field Must Not Be Empty")
	@Column(unique = true)
	private String code;
	
	private String nature;
	
	private Double credit;
	
	@PositiveOrZero(message = "Client Mouvement Must Be Positive Number Or Zero")
	private Double mvt;
	
	private String phone;
	
	private String address;
	
	@Email
	private String email;

	private AppUser user;

}

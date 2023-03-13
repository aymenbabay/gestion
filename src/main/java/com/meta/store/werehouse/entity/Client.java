package com.meta.store.werehouse.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.meta.store.base.Entity.BaseEntity;
import com.meta.store.base.security.entity.AppUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="client_werehouse")
public class Client extends BaseEntity<Long> {


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
	
	@OneToOne()
	@JoinColumn(name = "user_id")
	private AppUser user;
	

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "company_client",
				joinColumns = @JoinColumn(name="client_id",referencedColumnName = "id"),
				inverseJoinColumns = @JoinColumn(name="company_id",referencedColumnName = "id"))
	private Set<Company> companies = new HashSet<>();

	public Client(@NotBlank(message = "Client Name Field Must Not Be Empty") String name,
			@NotBlank(message = "Client Code Field Must Not Be Empty") String code, @Email String email) {
		super();
		this.name = name;
		this.code = code;
		this.email = email;
	}
	
	
	
	
}

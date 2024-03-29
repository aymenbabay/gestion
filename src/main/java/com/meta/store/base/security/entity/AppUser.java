package com.meta.store.base.security.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.meta.store.base.Entity.BaseEntity;
import com.meta.store.werehouse.entity.Company;
import com.meta.store.werehouse.entity.Worker;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sec_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser extends BaseEntity<Long> implements UserDetails {

	
	private String phone;
	
	private String address;
	
	@NotBlank(message = "User Name Field Must Be Not Empty")
	private String userName;
	
	private String email;

	@NotBlank(message = "Password Field Must Be Not Empty")
	private String password;
	
	private String resetToken;
	
	private LocalDateTime dateToken;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name ="userId"), inverseJoinColumns = @JoinColumn(name="roleId"))
	private Set<Role> roles = new HashSet<>();
//
//	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
//	private Worker worker;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public AppUser(String phone, String userName, String email, String password, Set<Role> roles) {
		super();
		this.phone = phone;
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}

	

	
}

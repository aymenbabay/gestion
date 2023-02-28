package com.meta.store.base.security.entity;

import java.util.HashSet;
import java.util.Set;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sec_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppUser extends BaseEntity<Long> {

	private String fullName;
	
	private String userName;
	
	private String password;
	
	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name ="userId"), inverseJoinColumns = @JoinColumn(name="roleId"))
	private Set<Role> roles = new HashSet<>();
}

package com.meta.store.base.security.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.meta.store.base.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
public class AppUser extends BaseEntity<Long> implements UserDetails{

	

	private String firstname;
	
	private String userName;
	
	private String email;
	
	private String password;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name ="userId"), inverseJoinColumns = @JoinColumn(name="roleId"))
	private Set<Role> roles = new HashSet<>();

	
	
	

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

	
}

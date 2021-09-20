package com.emp.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.emp.model.Manager;


public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private Manager manager;
	

	public CustomUserDetails(Manager manager) {
		super();
		this.manager = manager;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(manager.getRole());
		return List.of(simpleGrantedAuthority);
	}

	@Override
	public String getPassword() {	
		return manager.getManagerPassword();
	}

	@Override
	public String getUsername() {	
		return manager.getManagerEmail();
	}

	@Override
	public boolean isAccountNonExpired() {	
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {	
		return true;
	}

	@Override
	public boolean isEnabled() {	
		return true	;
	}
}

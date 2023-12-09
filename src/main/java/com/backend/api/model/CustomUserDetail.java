package com.backend.api.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.backend.domain.model.User;

public class CustomUserDetail implements UserDetails {

	private static final long serialVersionUID = 1L;
	private User user;

	Set<SimpleGrantedAuthority> authorities = null;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<SimpleGrantedAuthority> set) {
		this.authorities = set;
	}

	public String getPassword() {
		return user.getPassword();
	}

	public String getUsername() {
		return user.getEmail();
	}

	public boolean isAccountNonExpired() {
		return user.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
		return user.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
		return user.isCredentialsNonExpired();
	}

	public boolean isEnabled() {
		return user.isEnabled();
	}

}
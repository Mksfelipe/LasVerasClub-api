package com.backend.domain.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class CustomUserDetail implements UserDetails {

	 private static final long serialVersionUID = 1L;
	    private Client client;

	    Set<SimpleGrantedAuthority> authorities = null;

	    public Client getUser() {
	        return client;
	    }

	    public void setUser(Client client) {
	        this.client = client;
	    }

	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;
	    }

	    public void setAuthorities(Set<SimpleGrantedAuthority> set)
	    {
	        this.authorities = set;
	    }

	    public String getPassword() {
	        return client.getPassword();
	    }

	    public String getUsername() {
	        return client.getEmail();
	    }

	    public boolean isAccountNonExpired() {
	        return client.isAccountNonExpired();
	    }

	    public boolean isAccountNonLocked() {
	        return client.isAccountNonLocked();
	    }

	    public boolean isCredentialsNonExpired() {
	        return client.isCredentialsNonExpired();
	    }

	    public boolean isEnabled() {
	        return client.isEnabled();
	    }

}


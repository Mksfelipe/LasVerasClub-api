package com.backend.domain.service;

import java.util.HashSet;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.domain.model.Client;
import com.backend.domain.model.CustomUserDetail;
import com.backend.domain.repository.ClientRepository;

@Service(value = "userService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private ClientRepository clientRepository;

	public CustomUserDetail loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
		Client client = clientRepository.findByEmail(email)
		        .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
		
		CustomUserDetail customUserDetail = new CustomUserDetail();
		customUserDetail.setUser(client);
		customUserDetail.setAuthorities(getAuthority(client));
		
		return customUserDetail;

	}

	private Set<SimpleGrantedAuthority> getAuthority(Client client) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		client.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAuthority()));
		});
		return authorities;
	}

}

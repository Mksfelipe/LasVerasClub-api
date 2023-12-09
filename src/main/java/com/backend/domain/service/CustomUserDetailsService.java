package com.backend.domain.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.api.model.CustomUserDetail;
import com.backend.domain.model.User;
import com.backend.domain.repository.UserRepository;

@Service(value = "userService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	public CustomUserDetail loadUserByUsername(String email) throws UsernameNotFoundException, DataAccessException {
		User user = userRepository.findByEmail(email)
		        .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
		
		CustomUserDetail customUserDetail = new CustomUserDetail();
		customUserDetail.setUser(user);
		customUserDetail.setAuthorities(getAuthority(user));
		
		return customUserDetail;

	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getAuthority()));
		});
		return authorities;
	}

}

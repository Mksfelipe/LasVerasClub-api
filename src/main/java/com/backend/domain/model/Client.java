package com.backend.domain.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;

import com.backend.domain.model.converter.BooleanConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class Client extends CustomUserDetail {

	private static final long serialVersionUID = 1L;

	@Getter
	@Setter
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_id_clients")
	private Long id;

	@Getter
	private String firstName;

	@Getter
	private String lastName;

	@Getter
	@Setter
	@CPF
	@Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas números e ter 11 dígitos")
	private String cpf;

	@NotBlank
	@Getter
	@Setter
	private String password;

	@NotBlank
	@Email
	@Getter
	@Setter
	private String email;

	@Convert(converter = BooleanConverter.class)
	@NotNull
	@Getter
	private Boolean enable = true;

	@JsonIgnore
	@Getter
	@Setter
	@ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinTable(name = "client_roles", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Set<Reserva> reservas;

	public void disable() {
		this.enable = false;
	}

	public void enable() {
		this.enable = true;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName.toUpperCase();
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.toUpperCase();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getUsername() {
		return this.firstName;
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
		return this.enable;
	}
}
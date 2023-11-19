package com.backend.domain.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.hibernate.annotations.Where;

import com.backend.domain.model.converter.BooleanConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Where(clause = "ativo = 'Y'")
public class Quadra {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long id;

	private String nome;

	@Convert(converter = BooleanConverter.class)
	private Boolean ativo = true;
	
	@NotNull
	@Positive
	private BigDecimal pricePerHour;

	@JsonIgnore
	@OneToMany(mappedBy = "quadra", cascade = CascadeType.ALL)
	private List<Reserva> reservas;

}

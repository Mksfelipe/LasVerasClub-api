package com.backend.domain.model;

import javax.persistence.Convert;
import javax.persistence.Entity;

import org.hibernate.annotations.Where;

import com.backend.domain.model.converter.BooleanConverter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Where(clause = "ativo = 'Y'")
public class ReservaFixa extends Reserva {

	@Convert(converter = BooleanConverter.class)
	private Boolean segunda = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean terca = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean quarta = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean quinta = false;
	@Convert(converter = BooleanConverter.class)
	private Boolean sexta = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean sabado = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean domingo = false;
	
}

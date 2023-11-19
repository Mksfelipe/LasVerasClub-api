package com.backend.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Where;

import com.backend.domain.model.converter.BooleanConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Where(clause = "ativo = 'Y'")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reserva {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long id;

	@Getter
	@Setter
	private LocalDate data;
	
	@Getter
	@Setter
	private OffsetTime horarioInicio;
	
	@Getter
	@Setter
	private OffsetTime horarioFim;

	@JsonIgnore
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quadra_id")
	private Quadra quadra;

	@JsonIgnore
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client cliente;

	@Getter
	@Setter
	private BigDecimal amount = BigDecimal.ZERO;
	
	@Getter
	@Setter
	private int numberParticipants = 1;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean ativo = true;

	@Getter
	@Setter
	@Enumerated(EnumType.ORDINAL)
	private TypeSport typeSport;
	
	@Getter
	@Setter
	private BigDecimal individualValue = BigDecimal.ZERO;

	public void calcularPreco() {
		
	    long minutosReservados = Duration.between(horarioInicio, horarioFim).toMinutes();
	    int horasReservadas = (int) Math.ceil(minutosReservados / 60.0);

	    BigDecimal precoPorHora = quadra.getPricePerHour();

	    this.amount = precoPorHora.multiply(BigDecimal.valueOf(horasReservadas));
	    calcularValorIndividual();

	}
	
	private void calcularValorIndividual() {
	    if (numberParticipants > 0) {
	        this.individualValue = this.amount.divide(BigDecimal.valueOf(numberParticipants), 2, RoundingMode.HALF_UP);
	    }
	}

    @PrePersist
    private void retrocederUmSegundoDataFinal() {
    	this.horarioFim.minusMinutes(1);
    }
}

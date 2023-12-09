package com.backend.domain.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Where(clause = "ativo = 'Y'")
public class ReservaFixa {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@EqualsAndHashCode.Include
	private Long id;

	@Getter
	@Setter
	private LocalDate data = LocalDate.now();
	
	@Getter
	@Setter
	private OffsetTime horarioInicio;
	
	@Getter
	@Setter
	private OffsetTime horarioFim;
	
	@Getter
	@Setter
	private BigDecimal individualValue = BigDecimal.ZERO;

	@JsonIgnore
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "quadra_id")
	private Quadra quadra;

	@JsonIgnore
	@Getter
	@Setter
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

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

	@Convert(converter = BooleanConverter.class)public void calcularPreco() {
		
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

	@Convert(converter = BooleanConverter.class)
	private Boolean segunda = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean terca = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean quarta = false;
    
	private Boolean quinta = false;
	@Convert(converter = BooleanConverter.class)
	private Boolean sexta = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean sabado = false;
	
	@Convert(converter = BooleanConverter.class)
	private Boolean domingo = false;

	@JsonIgnore
	public List<Boolean> getDiasSemanaAsList() {
        return Arrays.asList(segunda, terca, quarta, quinta, sexta, sabado, domingo);
    }
	
}

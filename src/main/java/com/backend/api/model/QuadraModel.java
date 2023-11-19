package com.backend.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuadraModel {

	private Long id;
	private String nome;
	private BigDecimal pricePerHour;
	
}

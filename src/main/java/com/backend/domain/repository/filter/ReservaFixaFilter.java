package com.backend.domain.repository.filter;

import java.time.OffsetTime;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaFixaFilter {
	
	private OffsetTime horarioInicio;
	private OffsetTime horarioFim;
	private Map<String, Boolean> diasSemana;
	private Long quadraId;
}

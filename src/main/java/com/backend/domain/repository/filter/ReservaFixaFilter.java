package com.backend.domain.repository.filter;

import java.time.OffsetTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaFixaFilter {
	
	private OffsetTime horarioInicio;
	private OffsetTime horarioFim;
	private List<Boolean> diasSemana;
	private Long quadraId;
}

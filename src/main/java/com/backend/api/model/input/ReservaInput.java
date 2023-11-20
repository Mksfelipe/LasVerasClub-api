package com.backend.api.model.input;

import java.time.LocalDate;
import java.time.OffsetTime;

import javax.validation.constraints.NotNull;

import com.backend.domain.model.TypeSport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaInput {
	
	@NotNull
	private Long quadraId;
	@NotNull
	private Long clientId;
	private OffsetTime horarioInicio;
    private OffsetTime horarioFim;
    private Byte numberParticipants = 1;
    private TypeSport typeSport;
    private LocalDate data;
}

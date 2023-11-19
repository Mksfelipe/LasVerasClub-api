package com.backend.api.model.input;

import java.time.OffsetTime;

import com.backend.domain.model.TypeSport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaInput {

	private OffsetTime horarioInicio;
    private OffsetTime horarioFim;
    private Byte numberParticipants = 1;
    private TypeSport typeSport;
	
}

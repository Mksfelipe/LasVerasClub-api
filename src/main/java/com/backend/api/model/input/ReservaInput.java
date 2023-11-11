package com.backend.api.model.input;

import java.time.OffsetDateTime;

import com.backend.domain.model.TypeSport;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservaInput {

	private OffsetDateTime horarioInicio;
    private OffsetDateTime horarioFim;
    private Byte numberParticipants;
    private TypeSport typeSport;
	
}

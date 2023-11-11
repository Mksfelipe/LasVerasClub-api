package com.backend.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.api.model.input.ReservaInput;
import com.backend.domain.model.Reserva;

@Component
public class ReservaInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Reserva toDomainObject(ReservaInput reservaInput) {
		return modelMapper.map(reservaInput, Reserva.class);
	}

	public void copyToDomainObject(ReservaInput reservaInput, Reserva reserva) {

		modelMapper.map(reservaInput, reserva);
	}
}

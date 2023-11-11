package com.backend.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.api.model.QuadraModel;
import com.backend.domain.model.Quadra;

@Component
public class QuadraInputDisassembler {

	@Autowired
	private ModelMapper modelMapper;

	public Quadra toDomainObject(QuadraModel reservaInput) {
		return modelMapper.map(reservaInput, Quadra.class);
	}

	public void copyToDomainObject(QuadraModel quadraInput, Quadra quadra) {

		modelMapper.map(quadraInput, quadra);
	}
}

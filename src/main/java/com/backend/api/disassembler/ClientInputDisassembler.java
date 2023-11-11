package com.backend.api.disassembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.api.model.input.ClientInputModel;
import com.backend.domain.model.Client;


@Component
public class ClientInputDisassembler {

	@Autowired
	private ModelMapper mapper;
	
	public Client toDomainObject(ClientInputModel clientInputModel) {
		return mapper.map(clientInputModel, Client.class);
	}
	
}

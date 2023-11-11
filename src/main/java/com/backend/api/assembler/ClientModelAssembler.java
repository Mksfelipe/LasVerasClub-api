package com.backend.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.api.model.ClientModel;
import com.backend.domain.model.Client;

@Component
public class ClientModelAssembler {

	@Autowired
	private ModelMapper mapper;
	
	public ClientModel toModel(Client client) {
		return mapper.map(client, ClientModel.class);
	}
	
	public List<ClientModel> toCollect(List<Client> clients) {
		return clients.stream().map(this::toModel).collect(Collectors.toList());
	}
}

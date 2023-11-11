package com.backend.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.assembler.ClientModelAssembler;
import com.backend.api.disassembler.ClientInputDisassembler;
import com.backend.api.model.ClientModel;
import com.backend.api.model.input.ClientInputModel;
import com.backend.domain.model.Client;
import com.backend.domain.service.ClientService;

@RestController
@RequestMapping("/admin")
public class ClientController {

	private final ClientService clientService;
	private final ClientInputDisassembler clientInputDisassembler;
	private final ClientModelAssembler clientModelAssembler;
	private BCryptPasswordEncoder encode;
	
	public ClientController(ClientService clientService, ClientInputDisassembler clientInputDisassembler, BCryptPasswordEncoder encode, ClientModelAssembler clientModelAssembler) {
		this.clientService = clientService;
		this.clientInputDisassembler = clientInputDisassembler;
		this.encode = encode;
		this.clientModelAssembler = clientModelAssembler;
	}
	
	@PostMapping("/client")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ClientModel save(@RequestBody ClientInputModel clientInputModel) {
		clientInputModel.setPassword(encode.encode(clientInputModel.getPassword()));
		
		Client client = clientInputDisassembler.toDomainObject(clientInputModel);
		
		return clientModelAssembler.toModel(clientService.save(client));
	}
	
	@PutMapping("/client/{clientId}/disable")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void clientDisable(@PathVariable Long clientId) {
		clientService.disableClient(clientId);
	}
	
}

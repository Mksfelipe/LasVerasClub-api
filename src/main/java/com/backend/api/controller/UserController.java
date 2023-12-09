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
import com.backend.api.disassembler.UserInputDisassembler;
import com.backend.api.model.UserModel;
import com.backend.api.model.input.UserInputModel;
import com.backend.domain.model.User;
import com.backend.domain.service.ClientService;

@RestController
@RequestMapping("/admin")
public class UserController {

	private final ClientService userService;
	private final UserInputDisassembler userInputDisassembler;
	private final ClientModelAssembler clientModelAssembler;
	private BCryptPasswordEncoder encode;
	
	public UserController(ClientService userService, UserInputDisassembler userInputDisassembler, BCryptPasswordEncoder encode, ClientModelAssembler clientModelAssembler) {
		this.userService = userService;
		this.userInputDisassembler = userInputDisassembler;
		this.encode = encode;
		this.clientModelAssembler = clientModelAssembler;
	}
	
	@PostMapping("/client")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public UserModel save(@RequestBody UserInputModel userInputModel) {
		userInputModel.setPassword(encode.encode(userInputModel.getPassword()));
		
		User user = userInputDisassembler.toDomainObject(userInputModel);
		
		return clientModelAssembler.toModel(userService.save(user));
	}
	
	@PutMapping("/client/{clientId}/disable")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void clientDisable(@PathVariable Long clientId) {
		userService.disableClient(clientId);
	}
	
}

package com.backend.domain.exception;

public class ClientNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public ClientNotFoundException(String mensagem) {
		super(mensagem);
	}

	public ClientNotFoundException(Long id) {
		this(String.format("Não existe um cadastro de cliente com código %d", id));
	}
}
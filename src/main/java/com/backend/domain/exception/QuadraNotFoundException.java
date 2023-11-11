package com.backend.domain.exception;

public class QuadraNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public QuadraNotFoundException(String mensagem) {
		super(mensagem);
	}

	public QuadraNotFoundException(Long id) {
		this(String.format("Não existe um cadastro de Quadra com ID: %d", id));
	}
}
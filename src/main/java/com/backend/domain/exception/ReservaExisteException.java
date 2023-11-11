package com.backend.domain.exception;

public class ReservaExisteException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public ReservaExisteException(String mensagem) {
		super(mensagem);
	}
	
	public ReservaExisteException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}

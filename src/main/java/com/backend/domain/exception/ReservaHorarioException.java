package com.backend.domain.exception;

public class ReservaHorarioException extends NegocioException {

	private static final long serialVersionUID = 1L;

	public ReservaHorarioException(String mensagem) {
		super(mensagem);
	}
	
	public ReservaHorarioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}

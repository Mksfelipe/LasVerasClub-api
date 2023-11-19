package com.backend.domain.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.domain.exception.ReservaExisteException;
import com.backend.domain.exception.ReservaHorarioException;
import com.backend.domain.model.Client;
import com.backend.domain.model.Quadra;
import com.backend.domain.model.Reserva;
import com.backend.domain.repository.ReservaRepository;

@Service
public class ReservaService {

	private ReservaRepository reservaRepository;

	public ReservaService(ReservaRepository reservaRepository) {
		this.reservaRepository = reservaRepository;
	}

	@Transactional
	public Reserva save(Quadra quadra, Reserva reserva, Client cliente, LocalDate dataDesejada) {

		if (reservaRepository.isHorarioDisponivel(quadra, dataDesejada, reserva.getHorarioInicio(),
				reserva.getHorarioFim())) {
			throw new ReservaExisteException(String.format("Horario ja cadastrado para a quadra %s, Horario: %s",
					quadra.getNome(), dataDesejada.toString()));
		}

		if (Duration.between(reserva.getHorarioInicio(), reserva.getHorarioFim()).toMinutes() < 30) {
			throw new ReservaHorarioException("Intervalo mínimo de 30 minutos não atendido");
		}

		if (reserva.getHorarioInicio().isAfter(reserva.getHorarioFim())) {
			throw new ReservaHorarioException("Data de início deve ser anterior à data de fim");
		}

		reserva.setQuadra(quadra);
		reserva.setCliente(cliente);
		reserva.calcularPreco();

		return reservaRepository.save(reserva);
	}

	public List<Reserva> findAllByQuadraId(Long quadraId) {
		return reservaRepository.findAllByQuadraId(quadraId);
	}

}

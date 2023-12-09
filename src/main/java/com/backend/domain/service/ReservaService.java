package com.backend.domain.service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.backend.domain.exception.ReservaExisteException;
import com.backend.domain.exception.ReservaHorarioException;
import com.backend.domain.model.Quadra;
import com.backend.domain.model.Reserva;
import com.backend.domain.model.User;
import com.backend.domain.repository.ReservaRepository;

@Service
public class ReservaService {

	private ReservaRepository reservaRepository;

	public ReservaService(ReservaRepository reservaRepository) {
		this.reservaRepository = reservaRepository;
	}

	@Transactional
	public Reserva save(Quadra quadra, Reserva reserva, User user, LocalDate dataDesejada) {

		if (!dataDesejada.isEqual(LocalDate.now()) && !dataDesejada.isAfter(LocalDate.now())) {
			throw new ReservaHorarioException("Data da reserva nao pode ser retroativa a data atual");
		}
		
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
		reserva.setUser(user);
		reserva.calcularPreco();

		return reservaRepository.save(reserva);
	}
	

	public List<Reserva> findAllByQuadraId(Long quadraId) {
		return reservaRepository.findAllByQuadraId(quadraId);
	}

}

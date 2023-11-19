package com.backend.domain.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.backend.domain.model.Reserva;
import com.backend.domain.model.ReservaFixa;
import com.backend.domain.repository.ReservaFixaRepository;
import com.backend.domain.repository.ReservaRepository;

@Service
public class ReservaFixaService {

	@Autowired
	private ReservaFixaRepository repositorioReservasFixas;

	@Autowired
	private ReservaRepository repositorioReservas;

	@Scheduled(cron = "*/20 * * * * *")
	public void gerarReservasParaClientesComReservaFixa() {
		// Busca todas as reservas fixas cadastradas
		List<ReservaFixa> reservasFixas = repositorioReservasFixas.findAll();

		// Obtém o último dia do mês atual
		LocalDate ultimoDiaDoMes = YearMonth.now().atEndOfMonth();

		// Para cada reserva fixa, cria uma reserva correspondente para cada dia do mês
		for (ReservaFixa reservaFixa : reservasFixas) {
			for (LocalDate dataReserva = LocalDate.now(); dataReserva
					.isBefore(ultimoDiaDoMes.plusDays(1)); dataReserva = dataReserva.plusDays(1)) {
				if (isDiaSelecionado(reservaFixa, dataReserva.getDayOfWeek())
						&& (!existeReservaParaIntervalo(reservaFixa, dataReserva))) {
					criarReservaParaDia(reservaFixa, dataReserva);

				}
			}
		}
	}

	private boolean isDiaSelecionado(ReservaFixa reservaFixa, DayOfWeek diaDaSemana) {
		switch (diaDaSemana) {
		case MONDAY:
			return reservaFixa.getSegunda();
		case TUESDAY:
			return reservaFixa.getTerca();
		case WEDNESDAY:
			return reservaFixa.getQuarta();
		case THURSDAY:
			return reservaFixa.getQuinta();
		case FRIDAY:
			return reservaFixa.getSexta();
		case SATURDAY:
			return reservaFixa.getSabado();
		case SUNDAY:
			return reservaFixa.getDomingo();
		default:
			return false;
		}
	}

	private boolean existeReservaParaIntervalo(ReservaFixa reservaFixa, LocalDate dataReserva) {
		return repositorioReservas.isHorarioDisponivel(reservaFixa.getQuadra(), dataReserva,
				reservaFixa.getHorarioInicio(), reservaFixa.getHorarioFim());
	}

	@Transactional
	private void criarReservaParaDia(ReservaFixa reservaFixa, LocalDate dataReserva) {
		Reserva reserva = new Reserva();
		reserva.setCliente(reservaFixa.getCliente());
		reserva.setQuadra(reservaFixa.getQuadra());
		reserva.setHorarioInicio(reservaFixa.getHorarioInicio());
		reserva.setHorarioFim(reservaFixa.getHorarioFim());
		reserva.setData(dataReserva);

		repositorioReservas.save(reserva);
	}
}

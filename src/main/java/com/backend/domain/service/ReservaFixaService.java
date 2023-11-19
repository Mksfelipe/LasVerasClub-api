package com.backend.domain.service;

import java.time.DayOfWeek;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.backend.domain.exception.ReservaExisteException;
import com.backend.domain.model.Reserva;
import com.backend.domain.model.ReservaFixa;
import com.backend.domain.repository.ReservaFixaRepository;
import com.backend.domain.repository.filter.ReservaFixaFilter;

@Service
public class ReservaFixaService {
	
	 Logger logger = LoggerFactory.getLogger(ReservaFixaService.class);

	@Autowired
	private ReservaFixaRepository repositorioReservasFixas;

	@Autowired
	private ReservaService reservaService;

	public ReservaFixa save(ReservaFixa reservaFixa) {

		ReservaFixaFilter filter = new ReservaFixaFilter();

		filter.setHorarioInicio(reservaFixa.getHorarioInicio());
		filter.setHorarioFim(reservaFixa.getHorarioFim());
		filter.setQuadraId(reservaFixa.getQuadra().getId());
		filter.setDiasSemana(reservaFixa.getDiasSemanaAsList());

		if (repositorioReservasFixas.existsReservaFixaNoMesmoDiaEHorarioEQuadra(filter)) {
			throw new ReservaExisteException(
					String.format("Já Existe uma Reserva fixa cadastrada nesse Horario na quadra: %s, Nos dias: %s",
							reservaFixa.getQuadra().getNome(), obterNomesDias(filter.getDiasSemana())));
		}

		
		reservaFixa.calcularPreco();
		reservaFixa.setData(LocalDate.now());
		return repositorioReservasFixas.save(reservaFixa);
	}

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
				if (isDiaSelecionado(reservaFixa, dataReserva.getDayOfWeek())) {
					try {
		                criarReservaParaDia(reservaFixa, dataReserva);
		            } catch (ReservaExisteException ex) {
		                logger.warn("Reserva já gerada para {}", reservaFixa.getData());
		            }
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

	private void criarReservaParaDia(ReservaFixa reservaFixa, LocalDate dataReserva) {
		Reserva reserva = new Reserva();
		reserva.setCliente(reservaFixa.getCliente());
		reserva.setHorarioInicio(reservaFixa.getHorarioInicio());
		reserva.setHorarioFim(reservaFixa.getHorarioFim());
		reserva.setData(dataReserva);
		reserva.setNumberParticipants(reservaFixa.getNumberParticipants());
		reserva.setTypeSport(reservaFixa.getTypeSport());
		
		reservaService.save(reservaFixa.getQuadra(), reserva, reserva.getCliente(), dataReserva);
	}
	
	private List<String> obterNomesDias(List<Boolean> diasSelecionados) {
	    List<String> nomesDias = new ArrayList<>();

	    if (Boolean.TRUE.equals(diasSelecionados.get(0))) nomesDias.add("Segunda-feira");
	    if (Boolean.TRUE.equals(diasSelecionados.get(1))) nomesDias.add("Terça-feira");
	    if (Boolean.TRUE.equals(diasSelecionados.get(2))) nomesDias.add("Quarta-feira");
	    if (Boolean.TRUE.equals(diasSelecionados.get(3))) nomesDias.add("Quinta-feira");
	    if (Boolean.TRUE.equals(diasSelecionados.get(4))) nomesDias.add("Sexta-feira");
	    if (Boolean.TRUE.equals(diasSelecionados.get(5))) nomesDias.add("Sábado");
	    if (Boolean.TRUE.equals(diasSelecionados.get(6))) nomesDias.add("Domingo");

	    return nomesDias;
	}
}

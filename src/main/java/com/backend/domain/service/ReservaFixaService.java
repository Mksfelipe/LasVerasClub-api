package com.backend.domain.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.backend.domain.exception.ReservaExisteException;
import com.backend.domain.model.Reserva;
import com.backend.domain.model.ReservaFixa;
import com.backend.domain.repository.ReservaFixaRepository;
import com.backend.domain.repository.ReservaRepository;
import com.backend.domain.repository.filter.ReservaFixaFilter;

@Service
public class ReservaFixaService {

	Logger logger = LoggerFactory.getLogger(ReservaFixaService.class);

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ReservaFixaRepository repositorioReservasFixas;
	
	@Autowired
	private ReservaRepository reservaRepository;

	@Autowired
	private ReservaService reservaService;
	
	public ReservaFixa save(ReservaFixa reservaFixa) {

		ReservaFixaFilter filter = modelMapper.map(reservaFixa, ReservaFixaFilter.class);
		
		if (repositorioReservasFixas.existsReservaFixaNoMesmoDiaEHorarioEQuadra(filter)) {
			throw new ReservaExisteException(
					String.format("Já Existe uma Reserva fixa cadastrada nesse Horario na quadra: %s, Nos dias: %s",
							reservaFixa.getQuadra().getNome(), obterNomesDias(filter.getDiasSemana())));
		}

		reservaFixa.calcularPreco();

		gerarReservasParaClientesComReservaFixa(reservaFixa);
		
		return reservaFixa;
	}

	public List<ReservaFixa> findAll() {
		return repositorioReservasFixas.findAll();
	}
	
	@Scheduled(cron = "0 0 0 1 * *")
	private void gerarReservasParaClientesComReservaFixa() {
	    List<ReservaFixa> reservasFixas = repositorioReservasFixas.findAll();
	    gerarReservasParaClientes(reservasFixas);
	}

	public void gerarReservasParaClientesComReservaFixa(ReservaFixa reservaFixa) {
	    gerarReservasParaClientes(Collections.singletonList(reservaFixa));
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
		Reserva reserva = modelMapper.map(reservaFixa, Reserva.class);
		reserva.setData(dataReserva);
		repositorioReservasFixas.save(reservaFixa);
		
		reserva.setReservaFixa(reservaFixa);
		
		reservaService.save(reservaFixa.getQuadra(), reserva, reserva.getUser(), dataReserva);
	}

	private List<String> obterNomesDias(List<Boolean> diasSelecionados) {
		List<String> nomesDias = new ArrayList<>();

		if (Boolean.TRUE.equals(diasSelecionados.get(0)))
			nomesDias.add("Segunda-feira");
		if (Boolean.TRUE.equals(diasSelecionados.get(1)))
			nomesDias.add("Terça-feira");
		if (Boolean.TRUE.equals(diasSelecionados.get(2)))
			nomesDias.add("Quarta-feira");
		if (Boolean.TRUE.equals(diasSelecionados.get(3)))
			nomesDias.add("Quinta-feira");
		if (Boolean.TRUE.equals(diasSelecionados.get(4)))
			nomesDias.add("Sexta-feira");
		if (Boolean.TRUE.equals(diasSelecionados.get(5)))
			nomesDias.add("Sábado");
		if (Boolean.TRUE.equals(diasSelecionados.get(6)))
			nomesDias.add("Domingo");

		return nomesDias;
	}
	
	private void gerarReservasParaClientes(List<ReservaFixa> reservasFixas) {
	    LocalDate ultimoDiaDoMes = YearMonth.now().atEndOfMonth();

	    for (ReservaFixa reservaFixa : reservasFixas) {
	        for (LocalDate dataReserva = LocalDate.now(); dataReserva.isBefore(ultimoDiaDoMes.plusDays(1)); dataReserva = dataReserva.plusDays(1)) {
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
	
	public void cancelarReservaFixaAssociadas(Long idReservaFixa) {
        ReservaFixa reservaFixa = repositorioReservasFixas.findById(idReservaFixa)
                .orElseThrow(() -> new ReservaExisteException("Reserva Fixa não encontrada com o ID: " + idReservaFixa));

        cancelarReservasAssociadas(reservaFixa);
        reservaFixa.setAtivo(false);
        repositorioReservasFixas.save(reservaFixa);
    }

    private void cancelarReservasAssociadas(ReservaFixa reservaFixa) {
        List<Reserva> reservasAssociadas = reservasAssociadas(reservaFixa);
        for (Reserva reserva : reservasAssociadas) {
        	reserva.setAtivo(false);
        	reservaRepository.save(reserva);
        }
    }

    private List<Reserva> reservasAssociadas(ReservaFixa reservaFixa) {
    	return reservaRepository.findAllByReservaFixa(reservaFixa.getId());
    }
}

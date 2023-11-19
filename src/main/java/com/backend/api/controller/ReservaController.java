package com.backend.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.assembler.ReservaInputDisassembler;
import com.backend.api.model.input.ReservaInput;
import com.backend.domain.model.Client;
import com.backend.domain.model.Quadra;
import com.backend.domain.model.Reserva;
import com.backend.domain.repository.ReservaFixaRepository;
import com.backend.domain.service.ClientService;
import com.backend.domain.service.QuadraService;
import com.backend.domain.service.ReservaService;

@RestController
@RequestMapping("/quadra/{quadraId}/reserva")
public class ReservaController {

	private final ReservaService reservaService;
	private final QuadraService quadraService;
	private final ClientService clientService;
	private final ReservaInputDisassembler disassembler;
	
	public ReservaController(ReservaService reservaService, QuadraService quadraService,
			ClientService clientService, ReservaInputDisassembler disassembler) {
		this.quadraService = quadraService;
		this.reservaService = reservaService;
		this.clientService = clientService;
		this.disassembler = disassembler;
	}

	@GetMapping
	public List<Reserva> reservaQuadra(@PathVariable Long quadraId) {
		return reservaService.findAllByQuadraId(quadraId);
	}

	@PostMapping("/client/{clientId}")
	public Reserva save(@PathVariable Long quadraId, @RequestBody ReservaInput reservaInput, @PathVariable Long clientId) {
		Reserva reserva = disassembler.toDomainObject(reservaInput);
		Client client = clientService.findById(clientId);
		Quadra quadra = quadraService.findById(quadraId);
		
		
		reservaService.save(quadra, reserva, client, LocalDate.now());

		return reserva;
	}
	
	

}

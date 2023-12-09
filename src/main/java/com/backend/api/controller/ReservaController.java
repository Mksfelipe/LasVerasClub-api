package com.backend.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.assembler.ReservaInputDisassembler;
import com.backend.api.model.input.ReservaInput;
import com.backend.domain.model.Quadra;
import com.backend.domain.model.Reserva;
import com.backend.domain.model.User;
import com.backend.domain.service.ClientService;
import com.backend.domain.service.QuadraService;
import com.backend.domain.service.ReservaService;

@RestController
@RequestMapping("/quadra/{quadraId}/reserva")
public class ReservaController {

	private final ReservaService reservaService;
	private final QuadraService quadraService;
	private final ClientService userService;
	private final ReservaInputDisassembler disassembler;
	
	public ReservaController(ReservaService reservaService, QuadraService quadraService,
			ClientService userService, ReservaInputDisassembler disassembler) {
		this.quadraService = quadraService;
		this.reservaService = reservaService;
		this.userService = userService;
		this.disassembler = disassembler;
	}

	@GetMapping
	public List<Reserva> reservaQuadra(@PathVariable Long quadraId) {
		Quadra quadra = quadraService.findById(quadraId);
		return reservaService.findAllByQuadraId(quadra.getId());
	}

	@PostMapping("/user/{userId}")
	public Reserva save(@PathVariable Long quadraId, @RequestBody ReservaInput reservaInput, @PathVariable Long userId) {
		Reserva reserva = disassembler.toDomainObject(reservaInput);
		User user = userService.findById(userId);
		Quadra quadra = quadraService.findById(quadraId);
		
		reservaService.save(quadra, reserva, user, reserva.getData());

		return reserva;
	}
	

}

package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.domain.model.Quadra;
import com.backend.domain.model.ReservaFixa;
import com.backend.domain.model.User;
import com.backend.domain.service.ClientService;
import com.backend.domain.service.QuadraService;
import com.backend.domain.service.ReservaFixaService;

@RestController
@RequestMapping("/quadra/{quadraId}/reservaFixa")
public class ReservaFixaController {

	@Autowired
	private ReservaFixaService reservaFixaService;
	
	@Autowired
	private QuadraService quadraService;
	
	@Autowired
	private ClientService userService;
	
	@PostMapping("/client/{clientId}")
	public ReservaFixa save(@PathVariable Long clientId, @PathVariable Long quadraId, @RequestBody ReservaFixa reservaFixa) {
		Quadra quadra = quadraService.findById(quadraId);
		User user = userService.findById(clientId);
		
		reservaFixa.setQuadra(quadra);
		reservaFixa.setUser(user);
		
		return reservaFixaService.save(reservaFixa);
	}
	
	@GetMapping
	public List<ReservaFixa> allReservasFixa() {
		return reservaFixaService.findAll();
	}
}

package com.backend.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.assembler.QuadraInputDisassembler;
import com.backend.api.assembler.QuadraModelAssembler;
import com.backend.api.model.QuadraModel;
import com.backend.domain.model.Quadra;
import com.backend.domain.service.QuadraService;

@RestController
@RequestMapping("/quadra")
public class QuadraController {

	private final QuadraService quadraService;
	private final QuadraModelAssembler quadraModelAssembler;
	private final QuadraInputDisassembler disassembler;

	public QuadraController(QuadraService quadraService, QuadraModelAssembler quadraModelAssembler, QuadraInputDisassembler disassembler) {
		this.quadraService = quadraService;
		this.quadraModelAssembler = quadraModelAssembler;
		this.disassembler = disassembler;
	}
	
	@GetMapping("/{quadraId}")
	public QuadraModel findById(@PathVariable Long quadraId) {
		return quadraModelAssembler.toModel(quadraService.findById(quadraId));
	}
	
	@GetMapping
	public List<Quadra> listAll() {
		return quadraService.findAll();
	}
	
	@PostMapping
	public Quadra save(@RequestBody Quadra quadra) {
		return quadraService.save(quadra);
	}
	
	@PutMapping("/{quadraId}")
	public QuadraModel update(@PathVariable Long quadraId, @RequestBody QuadraModel quadraModel) {
		Quadra quadra = disassembler.toDomainObject(quadraModel);
		return quadraModelAssembler.toModel(quadraService.update(quadra, quadraId));
	}
	
}

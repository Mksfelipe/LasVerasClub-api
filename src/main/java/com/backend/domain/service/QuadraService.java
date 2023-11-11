package com.backend.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.backend.domain.exception.QuadraNotFoundException;
import com.backend.domain.model.Quadra;
import com.backend.domain.repository.QuadraRepository;

@Service
public class QuadraService {

	private final QuadraRepository quadraRepository;

	public QuadraService(QuadraRepository quadraRepository) {
		this.quadraRepository = quadraRepository;
	}

	public Quadra findById(Long id) {
		return buscarOuFalhar(id);
	}

	public List<Quadra> findAll() {
		return quadraRepository.findAll();
	}

	@Transactional
	public Quadra save(Quadra quadra) {
		return quadraRepository.save(quadra);
	}

	@Transactional
	public Quadra update(Quadra quadra, Long quadraId) {
		Quadra quadraAtual = buscarOuFalhar(quadraId);
		BeanUtils.copyProperties(quadra, quadraAtual, "id");
		return quadraAtual;
	}

	private Quadra buscarOuFalhar(Long id) {
		return quadraRepository.findById(id)
				.orElseThrow(() -> new QuadraNotFoundException(String.format("Conta nao encontrada id: %d", id)));
	}

}

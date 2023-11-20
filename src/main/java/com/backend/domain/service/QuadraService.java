package com.backend.domain.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.backend.domain.exception.NegocioException;
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
		Optional<Quadra> quadraAtual = quadraRepository.findByNome(quadra.getNome());
		
		if (quadraAtual.isPresent()) {
			throw new NegocioException(String.format("Quadra com o nome: %s já existe", quadra.getNome()));
		}
		
		return quadraRepository.save(quadra);
	}

	@Transactional
	public Quadra update(Quadra quadra, Long quadraId) {
		Quadra quadraAtual = buscarOuFalhar(quadraId);
		BeanUtils.copyProperties(quadra, quadraAtual, "id");
		return quadraAtual;
	}

	@Transactional
	public void desativar(Long quadraId) {
		Quadra quadra = buscarOuFalhar(quadraId);
		quadra.desativar();
	}
	
	private Quadra buscarOuFalhar(Long id) {
		return quadraRepository.findById(id)
				.orElseThrow(() -> new QuadraNotFoundException(String.format("Conta nao encontrada id: %d", id)));
	}
	
}

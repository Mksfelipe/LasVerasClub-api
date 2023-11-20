package com.backend.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.domain.model.Quadra;

public interface QuadraRepository extends JpaRepository<Quadra, Long> {
	
	public List<Quadra> findAll();
	
	Optional<Quadra> findByNome(String nome);
	
}

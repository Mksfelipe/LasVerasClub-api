package com.backend.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.domain.model.Quadra;

public interface QuadraRepository extends JpaRepository<Quadra, Long> {
	
	public List<Quadra> findAll();
	
}

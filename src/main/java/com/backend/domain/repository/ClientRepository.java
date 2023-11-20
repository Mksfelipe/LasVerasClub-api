package com.backend.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.domain.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

	@Query("SELECT c FROM Client c JOIN FETCH c.roles where c.email = ?1")
	public Optional<Client> findByEmail(String email);
	
	@Query("SELECT c FROM Client c where c.cpf = ?1")
	public Optional<Client> findByCpf(String cpf);
	
	
}

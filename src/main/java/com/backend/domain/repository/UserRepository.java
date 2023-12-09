package com.backend.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.domain.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	@Query("SELECT c FROM User c JOIN FETCH c.roles where c.email = ?1")
	public Optional<User> findByEmail(String email);
	
	@Query("SELECT c FROM User c where c.cpf = ?1")
	public Optional<User> findByCpf(String cpf);
	
	
}

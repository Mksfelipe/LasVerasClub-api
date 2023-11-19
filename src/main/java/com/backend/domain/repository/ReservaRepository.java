package com.backend.domain.repository;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.domain.model.Quadra;
import com.backend.domain.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

	@Query("SELECT COUNT(r) > 0 FROM Reserva r " +
		       "WHERE (:dataDesejada = r.data AND " +
		       "      (:horarioDesejado BETWEEN r.horarioInicio AND r.horarioFim " +
		       "       OR :horarioDesejadoFim BETWEEN r.horarioInicio AND r.horarioFim " +
		       "       OR (:horarioDesejado <= r.horarioInicio AND :horarioDesejadoFim >= r.horarioFim))) " +
		       "AND r.quadra = :quadra")
		boolean isHorarioDisponivel(@Param("quadra") Quadra quadra, 
		                             @Param("dataDesejada") LocalDate dataDesejada,
		                             @Param("horarioDesejado") OffsetTime horarioDesejado,
		                             @Param("horarioDesejadoFim") OffsetTime horarioDesejadoFim);

	
	@Query("SELECT r FROM Reserva r WHERE r.quadra.id = :quadraId")
	List<Reserva> findAllByQuadraId(@Param("quadraId") Long quadraId);

}

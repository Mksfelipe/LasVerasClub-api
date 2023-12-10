package com.backend.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.domain.model.ReservaFixa;
import com.backend.domain.repository.filter.ReservaFixaFilter;

public interface ReservaFixaRepository extends JpaRepository<ReservaFixa, Long> {

	@Query("SELECT COUNT(r) > 0 FROM ReservaFixa r " +
	        "WHERE r.horarioInicio = :#{#filtro.horarioInicio} " +
	        "AND r.horarioFim = :#{#filtro.horarioFim} " +
	        "AND (COALESCE(:#{#filtro.diasSemana['segunda']}, true) = true OR r.segunda = false) " +
	        "AND (COALESCE(:#{#filtro.diasSemana['terca']}, true) = true OR r.terca = false) " +
	        "AND (COALESCE(:#{#filtro.diasSemana['quarta']}, true) = true OR r.quarta = false) " +
	        "AND (COALESCE(:#{#filtro.diasSemana['quinta']}, true) = true OR r.quinta = false) " +
	        "AND (COALESCE(:#{#filtro.diasSemana['sexta']}, true) = true OR r.sexta = false) " +
	        "AND (COALESCE(:#{#filtro.diasSemana['sabado']}, true) = true OR r.sabado = false) " +
	        "AND (COALESCE(:#{#filtro.diasSemana['domingo']}, true) = true OR r.domingo = false) " +
	        "AND r.quadra.id = :#{#filtro.quadraId}")
	boolean existsReservaFixaNoMesmoDiaEHorarioEQuadra(@Param("filtro") ReservaFixaFilter filtro);


}
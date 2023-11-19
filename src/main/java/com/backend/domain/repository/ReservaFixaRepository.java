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
            "AND (:#{#filtro.diasSemana[0]} = true OR r.segunda = false) " +
            "AND (:#{#filtro.diasSemana[1]} = true OR r.terca = false) " +
            "AND (:#{#filtro.diasSemana[2]} = true OR r.quarta = false) " +
            "AND (:#{#filtro.diasSemana[3]} = true OR r.quinta = false) " +
            "AND (:#{#filtro.diasSemana[4]} = true OR r.sexta = false) " +
            "AND (:#{#filtro.diasSemana[5]} = true OR r.sabado = false) " +
            "AND (:#{#filtro.diasSemana[6]} = true OR r.domingo = false) " +
            "AND r.quadra.id = :#{#filtro.quadraId}")
    boolean existsReservaFixaNoMesmoDiaEHorarioEQuadra(@Param("filtro") ReservaFixaFilter filtro);
}
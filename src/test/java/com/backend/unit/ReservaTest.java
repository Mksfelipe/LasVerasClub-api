package com.backend.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.backend.domain.model.Client;
import com.backend.domain.model.Quadra;
import com.backend.domain.model.ReservaFixa;
import com.backend.domain.model.TypeSport;

class ReservaTest {
	private ReservaFixa reserva;

	@BeforeEach
    public void setUp() {
        reserva = new ReservaFixa();
        // Set up initial values for testing
        reserva.setData(LocalDate.now());
        reserva.setHorarioInicio(OffsetTime.now().plusHours(2));
        reserva.setHorarioFim(OffsetTime.now().plusHours(4));
        reserva.setQuadra(new Quadra()); // Assuming Quadra is another class in your codebase
        reserva.setCliente(new Client()); // Assuming Client is another class in your codebase
        reserva.setNumberParticipants(3);
        reserva.setTypeSport(TypeSport.BEACH_TENNIS);
        reserva.setSegunda(true);
        reserva.setTerca(true);
        reserva.setQuarta(true);
    }

    @Test
    void testCalculoPreco() {
    	reserva.getQuadra().setPricePerHour(new BigDecimal(30));
        reserva.calcularPreco();

        assertNotNull(reserva.getAmount());
        assertNotNull(reserva.getIndividualValue());

        assertEquals(0, reserva.getAmount().compareTo(new BigDecimal(60)), 0.01);
        assertEquals(0, reserva.getIndividualValue().compareTo(new BigDecimal(20)), 0.01);
    }

}

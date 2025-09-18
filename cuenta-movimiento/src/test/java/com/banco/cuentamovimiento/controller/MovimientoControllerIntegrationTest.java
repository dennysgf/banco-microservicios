package com.banco.cuentamovimiento.controller;

import com.banco.cuentamovimiento.application.dto.in.CuentaRequestDto;
import com.banco.cuentamovimiento.application.dto.in.MovimientoRequestDto;
import com.banco.cuentamovimiento.domain.enums.TipoCuenta;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class MovimientoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long crearCuenta(String numeroCuenta) throws Exception {
        CuentaRequestDto cuenta = new CuentaRequestDto();
        cuenta.setNumeroCuenta(numeroCuenta);
        cuenta.setTipoCuenta(TipoCuenta.AHORROS);
        cuenta.setSaldoInicial(1000.0);
        cuenta.setEstado(true);
        cuenta.setClienteId(1L);

        String response = mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuenta)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readTree(response).get("id").asLong();
    }

    @Test
    @DisplayName("Debe registrar un depósito y aumentar el saldo")
    void registrarDeposito() throws Exception {
        String numeroCuenta = "ACC-" + System.currentTimeMillis();
        Long cuentaId = crearCuenta(numeroCuenta);

        MovimientoRequestDto deposito = new MovimientoRequestDto();
        deposito.setCuentaId(cuentaId);
        deposito.setFecha(LocalDateTime.now());
        deposito.setTipoMovimiento("DEPOSITO");
        deposito.setValor(500.0);
        deposito.setSaldo(0.0);

        String response = mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deposito)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value(numeroCuenta))
                .andExpect(jsonPath("$.saldoDisponible").value(1500.0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        assert json.get("numeroCuenta").asText().equals(numeroCuenta);
        assert json.get("saldoDisponible").asDouble() == 1500.0;
    }

    @Test
    @DisplayName("Debe registrar un retiro y disminuir el saldo")
    void registrarRetiro() throws Exception {
        String numeroCuenta = "ACC-" + System.currentTimeMillis();
        Long cuentaId = crearCuenta(numeroCuenta);

        MovimientoRequestDto retiro = new MovimientoRequestDto();
        retiro.setCuentaId(cuentaId);
        retiro.setFecha(LocalDateTime.now());
        retiro.setTipoMovimiento("RETIRO");
        retiro.setValor(-200.0);
        retiro.setSaldo(0.0);

        String response = mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(retiro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value(numeroCuenta))
                .andExpect(jsonPath("$.saldoDisponible").value(800.0))
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        assert json.get("numeroCuenta").asText().equals(numeroCuenta);
        assert json.get("saldoDisponible").asDouble() == 800.0;
    }

    @Test
    @DisplayName("Debe fallar cuando el retiro supera el saldo disponible")
    void retiroSaldoInsuficiente() throws Exception {
        String numeroCuenta = "ACC-" + System.currentTimeMillis();
        Long cuentaId = crearCuenta(numeroCuenta);

        MovimientoRequestDto retiro = new MovimientoRequestDto();
        retiro.setCuentaId(cuentaId);
        retiro.setFecha(LocalDateTime.now());
        retiro.setTipoMovimiento("RETIRO");
        retiro.setValor(-2000.0);
        retiro.setSaldo(0.0);

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(retiro)))
                .andExpect(status().is4xxClientError())
                .andExpect(result ->
                        result.getResolvedException().getMessage().contains("Saldo no disponible"));
    }
}

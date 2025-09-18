package com.banco.cuentamovimiento.controller;

import com.banco.cuentamovimiento.application.dto.in.CuentaRequestDto;
import com.banco.cuentamovimiento.domain.enums.TipoCuenta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CuentaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Debe crear una cuenta  mediante el API: POST /cuentas")
    void crearCuenta() throws Exception {
        CuentaRequestDto request = new CuentaRequestDto();
        request.setNumeroCuenta("123456");
        request.setTipoCuenta(TipoCuenta.AHORROS);
        request.setSaldoInicial(1000.0);
        request.setEstado(true);
        request.setClienteId(1L);

        mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroCuenta").value("123456"))
                .andExpect(jsonPath("$.saldoInicial").value(1000.0));
    }

    @Test
    @DisplayName("Debe obtener una cuenta mediante el API: GET /cuentas/{id}")
    void obtenerCuenta() throws Exception {
        CuentaRequestDto request = new CuentaRequestDto();
        request.setNumeroCuenta("654321");
        request.setTipoCuenta(TipoCuenta.CORRIENTE);
        request.setSaldoInicial(2000.0);
        request.setEstado(true);
        request.setClienteId(1L);

        String response = mockMvc.perform(post("/api/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/cuentas/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.numeroCuenta").value("654321"));
    }
}

package com.banco.cuentamovimiento.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import com.banco.cuentamovimiento.application.dto.in.CuentaRequestDto;
import com.banco.cuentamovimiento.application.dto.in.MovimientoRequestDto;
import com.banco.cuentamovimiento.application.dto.out.ClienteResponseDto;
import com.banco.cuentamovimiento.domain.enums.TipoCuenta;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReporteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestTemplate restTemplate;

    @BeforeEach
    void setup() {
        ClienteResponseDto clienteFake = new ClienteResponseDto();
        clienteFake.setId(1L);
        clienteFake.setNombre("Cliente Test");

        when(restTemplate.getForObject(
                anyString(),
                eq(ClienteResponseDto.class)))
                .thenReturn(clienteFake);
    }
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

    private void crearMovimiento(Long cuentaId, String tipo, double valor) throws Exception {
        MovimientoRequestDto mov = new MovimientoRequestDto();
        mov.setCuentaId(cuentaId);
        mov.setFecha(LocalDateTime.now());
        mov.setTipoMovimiento(tipo);
        mov.setValor(valor);
        mov.setSaldo(0.0);

        mockMvc.perform(post("/api/movimientos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mov)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Debe generar un reporte de estado de cuenta con movimientos")
    void generarReporte() throws Exception {
        String numeroCuenta = "ACC-" + System.currentTimeMillis();
        Long cuentaId = crearCuenta(numeroCuenta);

        crearMovimiento(cuentaId, "DEPOSITO", 500.0);
        crearMovimiento(cuentaId, "RETIRO", -200.0);

        String fechaInicio = LocalDateTime.now().minusDays(1).withHour(0).withMinute(0).withSecond(0).toString();
        String fechaFin = LocalDateTime.now().plusDays(1).withHour(23).withMinute(59).withSecond(59).toString();


        mockMvc.perform(get("/api/reportes")
                        .param("fechaInicio", fechaInicio)
                        .param("fechaFin", fechaFin)
                        .param("clienteId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].numeroCuenta").value(numeroCuenta))
                .andExpect(jsonPath("$[0].saldoInicial").exists())
                .andExpect(jsonPath("$[0].movimiento").exists())
                .andExpect(jsonPath("$[0].saldoDisponible").exists());
    }
}

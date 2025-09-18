package com.banco.cuentamovimiento.infrastructure.messaging.consumer;

import com.banco.cuentamovimiento.application.dto.in.CuentaRequestDto;
import com.banco.cuentamovimiento.application.service.CuentaService;
import com.banco.cuentamovimiento.domain.enums.TipoCuenta;
import com.banco.cuentamovimiento.infrastructure.messaging.dto.out.ClienteCreadoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClienteEventConsumer {

    private final CuentaService cuentaService;

    @KafkaListener(
            topics = "cliente-creado",
            groupId = "cuenta-movimiento-group",
            containerFactory = "clienteKafkaListenerContainerFactory"
    )
    public void onClienteCreado(ClienteCreadoEvent event) {
        log.info("Evento recibido -> cliente-creado: {}", event);

        CuentaRequestDto cuenta = new CuentaRequestDto();
        cuenta.setNumeroCuenta("ACC-" + System.currentTimeMillis());
        cuenta.setTipoCuenta(TipoCuenta.AHORROS);
        cuenta.setSaldoInicial(0.0);
        cuenta.setEstado(true);
        cuenta.setClienteId(event.getId());

        cuentaService.crearCuenta(cuenta);
        log.info("Cuenta creada  para el cliente con el id {}", event.getId());
    }
}

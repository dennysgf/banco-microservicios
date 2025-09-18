package com.banco.cuentamovimiento.infrastructure.messaging.consumer;


import com.banco.cuentamovimiento.infrastructure.messaging.dto.out.MovimientoRegistradoEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovimientoEventConsumer {

    @KafkaListener(
            topics = "movimiento-registrado",
            groupId = "cuenta-movimiento-group",
            containerFactory = "movimientoKafkaListenerContainerFactory"
    )
    public void consumeMovimientoRegistrado(MovimientoRegistradoEvent event) {
        log.info("Evento recibido -> movimiento-registrado: {}", event);
    }
}

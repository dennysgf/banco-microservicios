package com.banco.cuentamovimiento.infrastructure.messaging.producer;

import com.banco.cuentamovimiento.infrastructure.messaging.dto.out.MovimientoRegistradoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovimientoEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishMovimientoRegistrado(MovimientoRegistradoEvent event) {
        kafkaTemplate.send("movimiento-registrado", event);
        log.info("Evento publicado en 'movimiento-registrado': {}", event);
    }
}

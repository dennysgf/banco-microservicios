package com.banco.clientepersona.infrastructure.messaging.producer;

import com.banco.clientepersona.infrastructure.messaging.ClienteActualizadoEvent;
import com.banco.clientepersona.infrastructure.messaging.ClienteCreadoEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishClienteCreado(ClienteCreadoEvent event) {
        kafkaTemplate.send("cliente-creado", event);
    }

    public void publishClienteActualizado(ClienteActualizadoEvent event) {
        kafkaTemplate.send("cliente-actualizado", event);
    }
}

package com.banco.cuentamovimiento.infrastructure.messaging.config;

import com.banco.cuentamovimiento.infrastructure.messaging.dto.out.ClienteCreadoEvent;
import com.banco.cuentamovimiento.infrastructure.messaging.dto.out.MovimientoRegistradoEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;


    @Bean
    public ConsumerFactory<String, ClienteCreadoEvent> clienteConsumerFactory() {
        JsonDeserializer<ClienteCreadoEvent> deserializer =
                new JsonDeserializer<>(ClienteCreadoEvent.class, false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClienteCreadoEvent> clienteKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ClienteCreadoEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(clienteConsumerFactory());
        return factory;
    }


    @Bean
    public ConsumerFactory<String, MovimientoRegistradoEvent> movimientoConsumerFactory() {
        JsonDeserializer<MovimientoRegistradoEvent> deserializer =
                new JsonDeserializer<>(MovimientoRegistradoEvent.class, false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MovimientoRegistradoEvent> movimientoKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, MovimientoRegistradoEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(movimientoConsumerFactory());
        return factory;
    }
}

package com.lostway.eventnotificator.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, EventChangeKafkaMessage> eventKafkaConsumerFactory() {
        return createConsumerFactory(EventChangeKafkaMessage.class);
    }

    @Bean
    public ConsumerFactory<String, EventStatusChangeKafkaMessage> eventStatusKafkaConsumerFactory() {
        return createConsumerFactory(EventStatusChangeKafkaMessage.class);
    }

    public <T> ConsumerFactory<String, T> createConsumerFactory(Class<T> clazz) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "event-notificator-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(clazz)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventChangeKafkaMessage> eventKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, EventChangeKafkaMessage>();
        factory.setConsumerFactory(eventKafkaConsumerFactory());
        factory.setMissingTopicsFatal(false);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventStatusChangeKafkaMessage> eventStatusKafkaListenerContainerFactory() {
        var factory = new ConcurrentKafkaListenerContainerFactory<String, EventStatusChangeKafkaMessage>();
        factory.setConsumerFactory(eventStatusKafkaConsumerFactory());
        factory.setMissingTopicsFatal(false);
        return factory;
    }
}

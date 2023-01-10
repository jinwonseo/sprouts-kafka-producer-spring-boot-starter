package kr.sprouts.autoconfigure.kafka.producer.configurations;

import kr.sprouts.autoconfigure.kafka.producer.properties.KafkaProducerProperties;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
@EnableConfigurationProperties(value = { KafkaProducerProperties.class })
@EnableKafka
public class KafkaProducerConfiguration {
    private final KafkaProducerProperties kafkaProducerProperties;

    public KafkaProducerConfiguration(KafkaProducerProperties kafkaProducerProperties) {
        this.kafkaProducerProperties = kafkaProducerProperties;

        LoggerFactory.getLogger(KafkaProducerConfiguration.class)
                .info(String.format("Initialized %s", KafkaProducerConfiguration.class.getName()));
    }

    @Bean
    public ProducerFactory<String, Object> defaultProducerFactory() {
        return new DefaultKafkaProducerFactory<>(this.kafkaProducerProperties.getProducerProperties());
    }

    @Bean
    public KafkaTemplate<String, Object> defaultKafkaTemplate() {
        return new KafkaTemplate<>(this.defaultProducerFactory());
    }

    @Bean
    public NewTopic defaultTopic() {
        return TopicBuilder.name(this.kafkaProducerProperties.getDefaultTopic().getName())
                .partitions(this.kafkaProducerProperties.getDefaultTopic().getPartitions())
                .replicas(this.kafkaProducerProperties.getDefaultTopic().getReplicas())
                .build();
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(this.kafkaProducerProperties.getAdminProperties());
    }
}

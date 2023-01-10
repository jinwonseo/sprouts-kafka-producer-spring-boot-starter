package kr.sprouts.autoconfigure.kafka.producer.configurations;

import org.apache.kafka.clients.admin.NewTopic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class KafkaProducerConfigurationTest {
    private final ApplicationContextRunner applicationContextRunner = new ApplicationContextRunner().withConfiguration(AutoConfigurations.of(
            KafkaProducerConfiguration.class
    ));

    @Test
    void bean() {
        String[] properties = {
                "sprouts.kafka.bootstrap-servers[0]=127.0.0.1:9092",
                "sprouts.kafka.bootstrap-servers[1]=127.0.0.1:9093",
                "sprouts.kafka.bootstrap-servers[2]=127.0.0.1:9094",
                "sprouts.kafka.default-topic.name=default-topic",
                "sprouts.kafka.default-topic.partitions=1",
                "sprouts.kafka.default-topic.replicas=1",
        };

        this.applicationContextRunner.withPropertyValues(properties).run(context -> {
            assertThat(context).hasSingleBean(KafkaProducerConfiguration.class);
            assertThat(context).hasSingleBean(ProducerFactory.class);
            assertThat(context).hasSingleBean(KafkaTemplate.class);
            assertThat(context).hasSingleBean(NewTopic.class);
            assertThat(context).hasSingleBean(KafkaAdmin.class);

            assertThat(context.getBean(NewTopic.class).name()).isEqualTo("default-topic");
            assertThat(context.getBean(NewTopic.class).numPartitions()).isEqualTo(Integer.valueOf("1"));
            assertThat(context.getBean(NewTopic.class).replicationFactor()).isEqualTo(Short.valueOf("1"));
        });
    }
}
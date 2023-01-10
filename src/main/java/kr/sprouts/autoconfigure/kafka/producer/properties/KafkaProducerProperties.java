package kr.sprouts.autoconfigure.kafka.producer.properties;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "sprouts.kafka")
public class KafkaProducerProperties {
    private List<String> bootstrapServers; // localhost:9092,localhost:9093

    private Topic defaultTopic;

    public Map<String, Object> getProducerProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return properties;
    }

    public Map<String, Object> getAdminProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());

        return properties;
    }

    public void setBootstrapServers(List<String> bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public List<String> getBootstrapServers() {
        return bootstrapServers;
    }

    public Topic getDefaultTopic() {
        return defaultTopic;
    }

    public void setDefaultTopic(Topic defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    public static class Topic {
        private String name;
        private Integer partitions;
        private Integer replicas;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getPartitions() {
            return partitions;
        }

        public void setPartitions(Integer partitions) {
            this.partitions = partitions;
        }

        public Integer getReplicas() {
            return replicas;
        }

        public void setReplicas(Integer replicas) {
            this.replicas = replicas;
        }
    }
}

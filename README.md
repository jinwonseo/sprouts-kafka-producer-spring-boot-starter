# sprouts-kafka-producer-spring-boot-starter

## Description

Spring boot starter with kafka producer configuration.

## Configurations

* maven
  ```xml
  <dependencies>
    <dependency>
      <groupId>kr.sprouts.autoconfigure</groupId>
      <artifactId>sprouts-kafka-producer-spring-boot-starter</artifactId>
      <version>0.0.1-SNAPSHOT</version>
    </dependency>
  </dependencies>
  ```

* gradle
  ```groovy
  dependencies {
    implementation 'kr.sprouts.autoconfigure:sprouts-sprouts-kafka-producer-spring-boot-starter-spring-boot-starter:0.0.1-SNAPSHOT'
  }
  ```
* application.yml
  ```yml
  sprouts:
    kafka:
      bootstrap-servers:
        - 127.0.0.1:9092
        - 127.0.0.1:9093
        - 127.0.0.1:9094
      default-topic:
        name: topic-name
        partitions: 1
        replicas: 1
  ```

* Example
  ```java
  import org.apache.kafka.clients.admin.NewTopic;
  import org.apache.kafka.clients.producer.ProducerRecord;
  import org.springframework.kafka.core.KafkaTemplate;
  import org.springframework.kafka.support.SendResult;
  import org.springframework.stereotype.Service;
  import org.springframework.util.concurrent.ListenableFuture;
  import org.springframework.util.concurrent.ListenableFutureCallback;
     
  @Service
  public class ExampleKafkaProducerService {
      private final KafkaTemplate<String, Object> defaultKafkaTemplate;
      private final NewTopic defaultTopic;
  
      public KafkaProducerService(KafkaTemplate<String, Object> defaultKafkaTemplate) {
          this.defaultKafkaTemplate = defaultKafkaTemplate;
          this.defaultTopic = defaultTopic;
      }
  
      public void produce(Object object) {
          ListenableFuture<SendResult<String, Object>> future = this.defaultKafkaTemplate.send(defaultTopic.name(), object);

          future.addCallback(new ListenableFutureCallback<>() {
              @Override
              public void onFailure(Throwable ex) {
                  // 예외 처리
              }

              @Override
              public void onSuccess(SendResult<String, Object> result) {
                  // 성공 처리
              }
        });
      }
  }
  ```

## Related dependencies
* [log4j](https://logging.apache.org/log4j/2.x/)
* [Spring kafka](https://spring.io/projects/spring-kafka)

package mincloud.example;

import java.io.IOException;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class kafkaProducer {

    public static void main(String[] args) throws IOException {

        Properties configs = new Properties();
        
        configs.put("bootstrap.servers", "localhost:9092");	// kafka host 및 server 설정
        configs.put("acks", "all");							            // 자신이 보낸 메시지에 대해 카프카로부터 확인을 기다리지 않습니다.
        configs.put("block.on.buffer.full", "true");		        // 서버로 보낼 레코드를 버퍼링 할 때 사용할 수 있는 전체 메모리의 바이트수
        configs.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");   // serialize 설정
        configs.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer"); // serialize 설정

        // Create Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(configs);
        
        // Send Message
        for (int i = 0; i < 10; i++) {
        	
            String v = "kafaka-test-message"+i;
            producer.send(new ProducerRecord<String, String>("kafka-test-topic", v));
        }
        
        producer.flush();
        producer.close();
    }

}
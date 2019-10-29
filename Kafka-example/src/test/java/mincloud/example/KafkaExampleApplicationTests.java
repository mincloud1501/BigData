package mincloud.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import mincloud.example.consumer.Receiver;
import mincloud.example.producer.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1,  topics = {KafkaExampleApplicationTests.KAFKA_TOPIC})

public class KafkaExampleApplicationTests {

	  static final String KAFKA_TOPIC = "kafka-test-topic";
	
	  @Autowired
	  private Receiver receiver;
	
	  @Autowired
	  private Sender sender;
	
	  @Test
	  public void testReceive() throws Exception {
		    sender.send("###Hello Spring Boot Kafka!!!");
		
		    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		    assertThat(receiver.getLatch().getCount()).isEqualTo(0);
	  }
}

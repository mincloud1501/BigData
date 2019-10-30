package mincloud.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.TimeUnit;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.context.junit4.SpringRunner;

import mincloud.example.consumer.Receiver;
import mincloud.example.model.Car;
import mincloud.example.producer.Sender;

@RunWith(SpringRunner.class)
@SpringBootTest

public class KafkaExampleApplicationTests {

      static final String RECEIVER_TOPIC = "kafka-test.t";
	
	  @Autowired
	  private Receiver receiver;
	
	  @Autowired
	  private Sender sender;

	  @ClassRule
	  public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, RECEIVER_TOPIC);

	  @Test
	  public void testReceive() throws Exception {
		    // Use JsonSerializer
		    Car car = new Car("Passat", "Volkswagen", "ABC-123");
		    sender.send(car);

		    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		    assertThat(receiver.getLatch().getCount()).isEqualTo(0);
	  }
	  
	  /*
	  public void testReceive() throws Exception {
		    
		    // Use StringSerializer
		  	int numberOfMessages = Receiver.COUNT;
		  	
		  	for (int i = 0; i < numberOfMessages; i++) {
		  		sender.send("message " + i);
		    }
		  	
		    //sender.send("###Hello Spring Boot Kafka!!!");
		
		    receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
		    assertThat(receiver.getLatch().getCount()).isEqualTo(0);
	  }*/
}

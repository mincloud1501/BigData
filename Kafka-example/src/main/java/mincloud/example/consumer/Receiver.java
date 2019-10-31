package mincloud.example.consumer;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import mincloud.example.avro.User;

public class Receiver {

		private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

		//public static final int COUNT = 20;
		
		private CountDownLatch latch = new CountDownLatch(1);

		public CountDownLatch getLatch() {
			  return latch;
		}

		//@KafkaListener(topics = "kafka-test.t")
		//@KafkaListener(id = "batch-listener", topics = "kafka-test.t")
		//@KafkaListener(topics = "${kafka.topic.avro}")
		@KafkaListener(topics = "kafka-test-topic")
		
		public void receive(User user) {
		     LOGGER.info("#####received user='{}'", user.toString());
		     latch.countDown();
	    }
		
		/*
		public void receive(List<String> data,
		      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
		      @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

		       LOGGER.info("start of batch receive");
		       
			   for (int i = 0; i < data.size(); i++) {
				   LOGGER.info("received message='{}' with partition-offset='{}'", data.get(i),
						   partitions.get(i) + "-" + offsets.get(i));
				   
				   			// handle message
				   			latch.countDown();
			    }
			    LOGGER.info("end of batch receive");
	    } */
		
		/*
		public void receive(ConsumerRecord<?, ?> consumerRecord) {
				LOGGER.info("received payload='{}'", consumerRecord.toString());
			    latch.countDown();
		}*/
}

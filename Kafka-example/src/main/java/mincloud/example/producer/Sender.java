package mincloud.example.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import mincloud.example.model.Car;

public class Sender {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);
	
	//@Value("${kafka.topic.json}")
	//private String jsonTopic;
	
	@Autowired
	private KafkaTemplate<String, Car> kafkaTemplate;
	
	public void send(Car car) {
		
	    //LOGGER.info("sending payload='{}'", payload);
	    //kafkaTemplate.send("kafka-test.t", payload);
		
		LOGGER.info("sending car='{}'", car.toString());
	    kafkaTemplate.send("kafka-test.t", car);
	 }
}
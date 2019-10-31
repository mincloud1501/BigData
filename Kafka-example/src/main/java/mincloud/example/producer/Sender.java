package mincloud.example.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import mincloud.example.avro.User;

public class Sender {

	private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);
	
	//@Value("${kafka.topic.json}")
	//private String jsonTopic;
	
	//@Value("${kafka.topic.avro}")
	//private String avroTopic;
	
	@Autowired
	private KafkaTemplate<String, User> kafkaTemplate;
	
	public void send(User user) {
		
	    //LOGGER.info("sending payload='{}'", payload);
	    //kafkaTemplate.send("kafka-test.t", payload);
		
		//LOGGER.info("#####sending car='{}'", car.toString());
	    //kafkaTemplate.send(jsonTopic, car);
		
		LOGGER.info("#####sending car='{}'", user.toString());
	    kafkaTemplate.send("kafka-test-topic", user);
	 }
}
package mincloud.example.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // KafkaConfiguration에서 작성한 Bean 주입.
    @Autowired
    KafkaTemplate kafkaTemplate;

    
    /**
     * /get?message=value 형태로 접근할 수 있도록 api 작성
     * @param message
     * @return
     */
    @RequestMapping(value="/get")
    public String getData(@RequestParam(value = "message", required = true, defaultValue = "") String message ){
        
    	LocalDateTime date = LocalDateTime.now();
        String dateStr = date.format(fmt);
        
        // topic(=kafka_test)에 time, message를 produce
        kafkaTemplate.send("kafka_test", dateStr + "   " + message);
        
        return "kafkaTemplate.send >>  " + message ;
    }
}

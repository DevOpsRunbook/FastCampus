package co.fastcampus.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

//EnableBinding will be Deprecated as of 3.1 in favor of functional programming model, stay tuned for the next tutorials
@EnableBinding(value = {OrderBinder.class})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

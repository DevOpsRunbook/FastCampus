package co.fastcampus;

import java.io.UnsupportedEncodingException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.context.config.annotation.EnableContextInstanceData;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableContextInstanceData
public class AwsKmsApplication {

    public static void main(String[] args) throws UnsupportedEncodingException {
        ApplicationContext ctx = SpringApplication.run(AwsKmsApplication.class, args);
    }
}

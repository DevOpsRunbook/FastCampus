package co.fastcampus.spring.boot.grpc.client.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class GrpcClientCommandLineRunner implements CommandLineRunner {
    @Autowired
    GrpcClientConfiguration configuration;

    @Override
    public void run(String... args) {

        configuration.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                configuration.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
    }
}

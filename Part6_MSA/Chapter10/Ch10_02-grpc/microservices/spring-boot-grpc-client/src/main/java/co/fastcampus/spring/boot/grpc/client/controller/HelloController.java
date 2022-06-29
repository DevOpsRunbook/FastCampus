package co.fastcampus.spring.boot.grpc.client.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import co.fastcampus.spring.boot.grpc.client.api.HelloWorldService;
import co.fastcampus.spring.boot.grpc.client.configuration.GrpcClientConfiguration;


@RestController
@Slf4j
public class HelloController {
    @Autowired
    GrpcClientConfiguration configuration;

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", defaultValue = "FastCampus", required = false) String name) {

        HelloWorldService.HelloRequest request = HelloWorldService.HelloRequest
                .newBuilder()
                .setName(name)
                .build();

        HelloWorldService.HelloResponse response = configuration.getStub().sayHello(request);
        log.info("Server response received: [{}]", response.getMessage());
        return response.getMessage();
    }
}

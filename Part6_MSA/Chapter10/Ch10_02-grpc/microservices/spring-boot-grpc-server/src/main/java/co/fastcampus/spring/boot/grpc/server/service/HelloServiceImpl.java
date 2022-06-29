package co.fastcampus.spring.boot.grpc.server.service;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import co.fastcampus.spring.boot.grpc.client.api.HelloWorldGrpc;
import co.fastcampus.spring.boot.grpc.client.api.HelloWorldService;

@Slf4j
@Component
public class HelloServiceImpl extends HelloWorldGrpc.HelloWorldImplBase {
    @Override
    public void sayHello(HelloWorldService.HelloRequest request,
                         StreamObserver<HelloWorldService.HelloResponse> responseObserver) {

        HelloWorldService.HelloResponse response = HelloWorldService.HelloResponse
                .newBuilder()
                .setMessage(String.format("Hello, %s. This message comes from gRPC.", request.getName()))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        log.info("Client Message Received：[{}]", request.getName());
    }
}

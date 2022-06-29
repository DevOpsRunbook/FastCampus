package co.fastcampus.spring.boot.grpc.client.configuration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import co.fastcampus.spring.boot.grpc.client.api.HelloWorldGrpc;

import java.util.concurrent.TimeUnit;


@Slf4j
@Component
public class GrpcClientConfiguration {

    @Value("${server-host}")
    private String host;


    @Value("${server-port}")
    private int port;

    private ManagedChannel channel;
    private HelloWorldGrpc.HelloWorldBlockingStub stub;

    public void start() {

        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();


        stub = HelloWorldGrpc.newBlockingStub(channel);
        log.info("gRPC client started, server address: {}:{}", host, port);
    }

    public void shutdown() throws InterruptedException {

        channel.shutdown().awaitTermination(1, TimeUnit.SECONDS);
        log.info("gRPC client shut down successfully.");
    }

    public HelloWorldGrpc.HelloWorldBlockingStub getStub() {
        return this.stub;
    }
}

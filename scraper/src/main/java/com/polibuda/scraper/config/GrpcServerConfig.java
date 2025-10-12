package com.polibuda.scraper.config;

import com.polibuda.scraper.grpc.EventServiceGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class GrpcServerConfig {

    private final EventServiceGrpcImpl eventService;

    @Value("${grpc.server.port:50051}")
    private int port;

    private Server server;

    @Bean(initMethod = "start")
    public Server grpcServer() {
        this.server = ServerBuilder.forPort(port)
                .addService(eventService)
                .build();
        return this.server;
    }

    @PreDestroy
    public void stop() throws InterruptedException {
        if (server != null) {
            log.info("Stopping gRPC server...");
            server.shutdown().awaitTermination();
        }
    }
}
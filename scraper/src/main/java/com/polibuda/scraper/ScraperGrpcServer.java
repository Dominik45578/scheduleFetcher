package com.polibuda.scraper;

import com.polibuda.scraper.grpc.EventServiceGrpcImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ScraperGrpcServer {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(ScraperGrpcServer.class, args);

        EventServiceGrpcImpl service = ctx.getBean(EventServiceGrpcImpl.class);
        Server server = ServerBuilder.forPort(50051)
                .addService(service)
                .build()
                .start();

        System.out.println("✅ gRPC Scraper server running on port 50051");
        server.awaitTermination();
    }
}
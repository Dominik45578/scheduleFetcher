package com.polibuda.auth.grpc;

import com.google.protobuf.Empty;
import com.polibuda.dto.EventDto;
import com.polibuda.dto.RRuleDto;
import com.polibuda.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Klient gRPC dla komunikacji z serwisem scraper.
 * Obsługuje pobieranie eventów, grup i fakultetów.
 */
@Slf4j
@Component
public class AuthGrpcClient {

    private final ManagedChannel channel;
    private final EventServiceGrpc.EventServiceBlockingStub blockingStub;

    public AuthGrpcClient(
            @Value("${scraper.grpc.host:localhost}") String host,
            @Value("${scraper.grpc.port:50051}") int port
    ) {
        this.channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        this.blockingStub = EventServiceGrpc.newBlockingStub(channel);

        log.info("Connected to scraper gRPC server at {}:{}", host, port);
    }

    /**
     * Pobiera listę wydarzeń przefiltrowanych po wydziale i grupach.
     */
    public List<EventDto> getFilteredEvents(String faculty, List<String> groups) {
        FilterRequest request = FilterRequest.newBuilder()
                .setFaculty(faculty)
                .addAllGroups(groups)
                .build();

        FilteredEventsResponse response = blockingStub.getFilteredEvents(request);

        return response.getEventsList().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Pobiera zestaw wszystkich unikalnych wydziałów z serwisu scraper.
     */
    public List<String> getFaculties() {
        FacultiesResponse response = blockingStub.getFaculties(Empty.getDefaultInstance());
        return response.getFacultiesList().stream()
                .map(Faculty::getCode)
                .collect(Collectors.toList());
    }

    /**
     * Pobiera zestaw wszystkich unikalnych grup z serwisu scraper.
     */
    public List<String> getGroups() {
        GroupsResponse response = blockingStub.getGroups(Empty.getDefaultInstance());
        return response.getGroupsList().stream()
                .map(Group::getCode)
                .collect(Collectors.toList());
    }

    /**
     * Zamyka kanał gRPC po zakończeniu pracy aplikacji.
     */
    @PreDestroy
    public void shutdown() {
        log.info("Shutting down gRPC channel...");
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
        }
    }

    // ====================================================
    // 🔧 Mapper Event -> EventDto
    // ====================================================

    private EventDto mapToDto(Event e) {
        return EventDto.builder()
                .duration(e.getDuration())
                .end(e.getEnd())
                .endDate(e.getEndDate())
                .eventType(e.getEventType())
                .faculty(e.getFaculty())
                .group(e.getGroup())
                .interval(e.getInterval())
                .instructor(e.getInstructor())
                .room(e.getRoom())
                .start(e.getStart())
                .startDate(e.getStartDate())
                .startTime(e.getStartTime())
                .title(e.getTitle())
                .rrule(
                        RRuleDto.builder()
                        .dtstart(e.getRrule().getDtstart())
                        .freq(e.getRrule().getFreq())
                        .until(e.getRrule().getUntil())
                        .interval(e.getRrule().getInterval())
                                .build())
                .build();
    }
}
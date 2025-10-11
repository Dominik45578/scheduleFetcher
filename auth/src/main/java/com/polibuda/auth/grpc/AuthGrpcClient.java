package com.polibuda.auth.grpc;

import com.polibuda.proto.Event;
import com.polibuda.proto.EventServiceGrpc;
import com.polibuda.proto.FilterRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AuthGrpcClient {

    private final EventServiceGrpc.EventServiceBlockingStub blockingStub;

    public AuthGrpcClient(
            @Value("${scraper.grpc.host:localhost}") String host,
            @Value("${scraper.grpc.port:50051}") int port
    ) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
        blockingStub = EventServiceGrpc.newBlockingStub(channel);
    }

    public List<com.polibuda.auth.dto.EventDto> getFilteredEvents(String faculty, List<String> groups) {
        FilterRequest request = FilterRequest.newBuilder()
                .setFaculty(faculty)
                .addAllGroups(groups)
                .build();

        var response = blockingStub.getFilteredEvents(request);
        return response.getEventsList().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private com.polibuda.auth.dto.EventDto mapToDto(Event e) {
        var dto = new com.polibuda.auth.dto.EventDto();
        dto.setDuration(e.getDuration());
        dto.setEnd(e.getEnd());
        dto.setEndDate(e.getEndDate());
        dto.setEventType(e.getEventType());
        dto.setFaculty(e.getFaculty());
        dto.setGroup(e.getGroup());
        dto.setInstructor(e.getInstructor());
        dto.setInterval(e.getInterval());
        dto.setRoom(e.getRoom());
        dto.setStart(e.getStart());
        dto.setStartDate(e.getStartDate());
        dto.setStartTime(e.getStartTime());
        dto.setTitle(e.getTitle());
        return dto;
    }
}
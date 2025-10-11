package com.polibuda.scraper.grpc;

import com.polibuda.dto.EventDto;
import com.polibuda.proto.Event;
import com.polibuda.proto.EventServiceGrpc;
import com.polibuda.proto.FilterRequest;
import com.polibuda.proto.FilteredEventsResponse;
import com.polibuda.scraper.service.EventService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceGrpcImpl extends EventServiceGrpc.EventServiceImplBase {

    private final EventService eventService;

    @Override
    public void getFilteredEvents(FilterRequest request, StreamObserver<FilteredEventsResponse> responseObserver) {

        var faculty = request.getFaculty();
        var groups = request.getGroupsList();

        var filtered = eventService.getEvents().stream()
                .filter(e -> e.getFaculty() != null && e.getFaculty().equalsIgnoreCase(faculty))
                .collect(Collectors.toList());

        var response = FilteredEventsResponse.newBuilder()
                .addAllEvents(filtered.stream().map(this::mapToProto).collect(Collectors.toList()))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Event mapToProto(EventDto dto) {
        return Event.newBuilder()
                .setDuration(nonNull(dto.getDuration()))
                .setEnd(nonNull(dto.getEnd()))
                .setEndDate(nonNull(dto.getEndDate()))
                .setEventType(nonNull(dto.getEventType()))
                .setFaculty(nonNull(dto.getFaculty()))
                .setGroup(nonNull(dto.getGroup()))
                .setInstructor(nonNull(dto.getInstructor()))
                .setInterval(dto.getInterval() == null ? 0 : dto.getInterval())
                .setRoom(nonNull(dto.getRoom()))
                .setStart(nonNull(dto.getStart()))
                .setStartDate(nonNull(dto.getStartDate()))
                .setStartTime(nonNull(dto.getStartTime()))
                .setTitle(nonNull(dto.getTitle()))
                .build();
    }

    private String nonNull(String s) {
        return s == null ? "" : s;
    }
}
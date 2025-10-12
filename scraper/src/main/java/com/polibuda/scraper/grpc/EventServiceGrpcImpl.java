package com.polibuda.scraper.grpc;

import com.google.protobuf.Empty;
import com.polibuda.dto.EventDto;
import com.polibuda.dto.FilterRequestDto;
import com.polibuda.proto.*;
import com.polibuda.scraper.service.EventFetcher;
import com.polibuda.scraper.service.EventFilterHandler;
import com.polibuda.scraper.service.EventService;
import com.polibuda.scraper.service.ExtractorService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Implementacja gRPC serwera, obsługująca zapytania o eventy, grupy oraz fakultety.
 */
@Service
@RequiredArgsConstructor
public class EventServiceGrpcImpl extends EventServiceGrpc.EventServiceImplBase {

    private final EventService eventService;
    private final EventFilterHandler eventFilterHandler;
//    private final ExtractorService extractorService;

    /**
     * Dotychczasowa metoda: filtrowanie eventów po wydziale.
     */
    @Override
    public void getFilteredEvents(FilterRequest request,
                                  StreamObserver<FilteredEventsResponse> responseObserver) {

        // dane wejściowe z requestu gRPC
        var faculty = request.getFaculty();
        var groups = request.getGroupsList();

        // przekazanie do handlera biznesowego
        var filterRequestDto = FilterRequestDto.builder()
                .faculty(faculty)
                .groups(groups)
                .build();

        // otrzymujemy Optional<List<EventDto>>
        var filteredOptional = eventFilterHandler.handleFiltering(filterRequestDto);

        // jeśli pusty, zwracamy pustą odpowiedź
        var events = filteredOptional.orElse(List.of());

        // mapowanie DTO -> proto
        var protoEvents = events.stream()
                .map(this::mapToProto)
                .collect(Collectors.toList());

        var response = FilteredEventsResponse.newBuilder()
                .addAllEvents(protoEvents)
                .build();

        // odpowiedź gRPC
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Nowa metoda: zwraca listę unikalnych wydziałów (FacultyName).
     */
    @Override
    public void getFaculties(Empty request, StreamObserver<FacultiesResponse> responseObserver) {
        // Twój EventService zwraca List<FacultyName>
        var facultyDtos = eventService.getFaculties();

        var protoFaculties = facultyDtos.stream()
                .map(f -> Faculty.newBuilder()       // mapowanie model -> proto
                        .setCode(f.getCode())
                        .build())
                .collect(Collectors.toList());

        FacultiesResponse response = FacultiesResponse.newBuilder()
                .addAllFaculties(protoFaculties)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getGroups(Empty request, StreamObserver<GroupsResponse> responseObserver) {
        // Twój EventService zwraca List<GroupName>
        var groupDtos = eventService.getGroups();

        var protoGroups = groupDtos.stream()
                .map(g -> Group.newBuilder()         // model -> proto
                        .setCode(g.getCode())
                        .build())
                .collect(Collectors.toList());

        GroupsResponse response = GroupsResponse.newBuilder()
                .addAllGroups(protoGroups)
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
                .setRrule(
                        RRule.newBuilder()
                                .setInterval(dto.getRrule().getInterval())
                                .setDtstart(dto.getRrule().getDtstart())
                                .setFreq(dto.getRrule().getFreq())
                                .setUntil(dto.getRrule().getUntil())
                                .build()
                )
                .build();
    }

    private String nonNull(String s) {
        return s == null ? "" : s;
    }
}
package com.polibuda.auth.rest;


import com.polibuda.auth.grpc.AuthGrpcClient;
import com.polibuda.auth.service.IcsService;
import com.polibuda.dto.EventDto;
import com.polibuda.model.FacultyName;
import com.polibuda.dto.FilterRequestDto;

import com.polibuda.model.GroupName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class FilteredEventController {

    private final IcsService icsService;
    private final AuthGrpcClient grpcClient;

    @PostMapping("/ics")
    public String filterEvents(@RequestBody FilterRequestDto request) {
        return icsService.getIcsFile(request);
    }
    @GetMapping("/groups")
    public List<String> getGroups() {
        return grpcClient.getGroups();
    }
    @GetMapping("/faculty")
    public List<String> getFaculty() {
        return grpcClient.getFaculties();
    }
    @PostMapping("/raw")
    public List<EventDto> getRaw(@RequestBody FilterRequestDto request) {
       return grpcClient.getFilteredEvents(request.getFaculty(),request.getGroups());
    }
}

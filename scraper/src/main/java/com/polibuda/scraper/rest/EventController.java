package com.polibuda.scraper.rest;

import com.polibuda.dto.EventDto;
import com.polibuda.dto.FilterRequestDto;
import com.polibuda.model.FacultyName;
import com.polibuda.model.GroupName;
import com.polibuda.scraper.service.EventFilterHandler;
import com.polibuda.scraper.service.EventService;
import com.polibuda.scraper.service.ExtractorService;
import com.polibuda.scraper.util.EventFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventFilterHandler eventFilterHandler;

    @GetMapping
    public String getAllEvents(){
        return eventService.getEvents().toString();
    }
    @PostMapping("/filter")
    public List<EventDto> filterEvents(@RequestBody FilterRequestDto request) {
       return eventFilterHandler.handleFiltering(request).orElse(List.of());
    }
    @GetMapping("/f")
    public List<FacultyName> getFaculties(){
        return eventService.getFaculties();
    }

    @GetMapping("/g")
    public List<GroupName> getGroups(){
        return eventService.getGroups();
    }

}

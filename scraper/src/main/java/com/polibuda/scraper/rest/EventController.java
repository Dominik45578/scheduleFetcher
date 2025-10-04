package com.polibuda.scraper.rest;

import com.polibuda.scraper.dto.EventDto;
import com.polibuda.scraper.dto.FilterRequestDto;
import com.polibuda.scraper.model.FacultyName;
import com.polibuda.scraper.model.GroupName;
import com.polibuda.scraper.service.EventService;
import com.polibuda.scraper.util.EventFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public String getAllEvents(){
        return eventService.getEvents().toString();
    }
    @PostMapping("/filter")
    public ResponseEntity<List<EventDto>> filterEvents(@RequestBody FilterRequestDto request) {
        FacultyName faculty;
        try {
            faculty = FacultyName.fromCode(request.getFaculty());
        } catch (IllegalArgumentException e) {
            log.error("Incorrect Faculty Name ", e);
            return ResponseEntity.badRequest().build(); // niepoprawny wydział
        }

        // 2️⃣ Parsowanie grup na listę enumów
        List<GroupName> groupEnums = request.getGroups().stream()
                .map(s -> {
                    try {
                        return GroupName.fromCode(s);
                    } catch (IllegalArgumentException ex) {
                        log.error("Incorrect Group Name {}", s);
                        return null; // ignorujemy niepoprawne grupy
                    }
                })
                .filter(g -> g != null)
                .collect(Collectors.toList());

        List<EventDto> filtered = EventFilterUtil.filterByFacultyAndGroups(eventService.getEvents(), faculty, groupEnums);

        return ResponseEntity.ok(filtered);
    }
}

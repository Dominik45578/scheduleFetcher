package com.polibuda.scraper.service;

import com.polibuda.dto.EventDto;
import com.polibuda.dto.FilterRequestDto;
import com.polibuda.model.FacultyName;
import com.polibuda.model.GroupName;
import com.polibuda.scraper.service.EventService;
import com.polibuda.scraper.util.EventFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Klasa odpowiedzialna za obsługę logiki filtrowania eventów.
 * Oddzielona od kontrolera dla czytelności i lepszej testowalności.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EventFilterHandler {

    private final EventService eventService;

    public Optional<List<EventDto>> handleFiltering(FilterRequestDto requestDto) {
        try {
            var filtered = EventFilterUtil.filterByFacultyAndGroups(
                            eventService.getEvents(),
                            parseFaculty(requestDto.getFaculty()),
                            parseGroups(requestDto.getGroups())
            );

            return Optional.of(filtered);
        } catch (IllegalArgumentException e) {
            log.error("Error parsing faculty or groups", e);
            return Optional.empty(); // można też rzucać dedykowany wyjątek
        } catch (Exception e) {
            log.error("Unexpected filtering error", e);
            return Optional.empty();
        }
    }

    /**
     * Mapowanie kodu semestru @FacultyName.
     */
    private FacultyName parseFaculty(String facultyCode) {
        return eventService.getFacultyFromCode(facultyCode);
    }

    /**
     * Mapowanie kodów grup na listę GroupName.
     * Pomija błędne wpisy, zapisując przy tym log błędu.
     */
    private List<GroupName> parseGroups(List<String> groupCodes) {
        return groupCodes.stream()
                .map(code -> {
                    try {
                        return eventService.getGroupFromCode(code);
                    } catch (IllegalArgumentException e) {
                        log.warn("Incorrect group code: {}", code);
                        return null;
                    }
                })
                .filter(g -> g != null)
                .collect(Collectors.toList());
    }
}
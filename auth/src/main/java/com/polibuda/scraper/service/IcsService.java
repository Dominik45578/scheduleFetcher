package com.polibuda.scraper.service;

import com.polibuda.scraper.dto.EventDto;
import com.polibuda.scraper.dto.FilterRequestDto;
import com.polibuda.scraper.model.CalendarFile;
import com.polibuda.scraper.utils.IcsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IcsService {

    private final FilteredEventService filteredEventService;
    private final CalendarService calendarService;

    /**
     * Buduje plik ICS na podstawie danych z filtrowanego EventDto
     */
    public String getIcsFile(FilterRequestDto filterRequest) {
        List<EventDto> filteredEvents = filteredEventService.getFilteredEvents(filterRequest);
        CalendarFile calendarFile = calendarService.buildCalendarFile(filteredEvents);
        return IcsBuilder.buildIcs(calendarFile);
    }
}

package com.polibuda.auth.service;

import com.polibuda.scraper.dto.EventDto;
import com.polibuda.scraper.dto.FilterRequestDto;
import com.polibuda.auth.model.CalendarFile;
import com.polibuda.auth.utils.IcsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IcsService {

    private final FilteredEventService filteredEventService;
    private final CalendarService calendarService;

    public String getIcsFile(FilterRequestDto filterRequest) {
        List<EventDto> filteredEvents = filteredEventService.getFilteredEvents(filterRequest);
        CalendarFile calendarFile = calendarService.buildCalendarFile(filteredEvents);
        return IcsBuilder.buildIcs(calendarFile);
    }
}

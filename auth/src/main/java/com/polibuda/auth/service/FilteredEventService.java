package com.polibuda.auth.service;

import com.polibuda.auth.FilteredEventFetcher;
import com.polibuda.scraper.dto.EventDto;
import com.polibuda.scraper.dto.FilterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilteredEventService {

    private final FilteredEventFetcher fetcher;

    public List<EventDto> getFilteredEvents(FilterRequestDto filterRequest) {
        Optional<List<EventDto>> events = fetcher.fetchEventsWithFilter(filterRequest);
        return events.orElse(List.of());
    }
}

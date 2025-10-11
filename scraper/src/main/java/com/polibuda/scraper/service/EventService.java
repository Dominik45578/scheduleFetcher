package com.polibuda.scraper.service;

import com.polibuda.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventFetcher fetcher;

    private List<EventDto> cachedEvents;
    public List<EventDto> getEvents() {
        if (cachedEvents != null) {
            List.copyOf(cachedEvents);
        }
        Optional<List<EventDto>> events = fetcher.fetchEvents();
        events.ifPresent(list -> cachedEvents = list);
        return cachedEvents;
    }

    public void refreshEvents() {
        cachedEvents = null;
        getEvents();
    }
}

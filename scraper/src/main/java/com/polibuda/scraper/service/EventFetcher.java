package com.polibuda.scraper.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polibuda.scraper.dto.EventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EventFetcher {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${scraper.temprest.complexurl}")
    private String firebaseUrl;

    public Optional<List<EventDto>> fetchEvents() {
        try {
            String json = restTemplate.getForObject(firebaseUrl, String.class);

            if (json == null || json.isBlank()) {
                return Optional.empty();
            }

            Map<String, EventDto> map = objectMapper.readValue(json, new TypeReference<>() {});
            List<EventDto> events = List.copyOf(map.values());

            return Optional.of(events);

        } catch (Exception e) {
            return Optional.empty();
        }
    }
}

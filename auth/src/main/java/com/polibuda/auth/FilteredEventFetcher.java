    package com.polibuda.auth;

    import com.fasterxml.jackson.core.type.TypeReference;
    import com.fasterxml.jackson.databind.ObjectMapper;
    import com.polibuda.auth.dto.EventDto;
    import com.polibuda.auth.dto.FilterRequestDto;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.http.HttpEntity;
    import org.springframework.http.HttpHeaders;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import org.springframework.web.client.RestTemplate;

    import java.util.List;
    import java.util.Map;
    import java.util.Optional;

    @Component
    @RequiredArgsConstructor
    public class FilteredEventFetcher {

        private final RestTemplate restTemplate;
        private final ObjectMapper objectMapper;

        @Value("${filtred.complexurl}")
        private String firebaseUrl;


        public Optional<List<EventDto>> fetchEventsWithFilter(FilterRequestDto filterRequest) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HttpEntity<FilterRequestDto> request = new HttpEntity<>(filterRequest, headers);

                ResponseEntity<String> response = restTemplate.postForEntity(firebaseUrl, request, String.class);

                String json = response.getBody();

                if (json == null || json.isBlank()) {
                    return Optional.empty();
                }

                List<EventDto> events = objectMapper.readValue(json, new TypeReference<>() {});
                return Optional.of(events);

            } catch (Exception e) {
                e.printStackTrace(); // warto logować błędy
                return Optional.empty();
            }
        }

    }

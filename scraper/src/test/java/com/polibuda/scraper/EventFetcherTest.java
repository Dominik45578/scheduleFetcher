//package com.polibuda.scraper;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.polibuda.scraper.dto.EventDto;
//import com.polibuda.scraper.service.EventFetcher;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.mockito.Mockito.*;
//import static org.assertj.core.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//class EventFetcherTest {
//
//    @Mock
//    private RestTemplate restTemplate;
//
//    @Mock
//    private ObjectMapper objectMapper;
//
//    @InjectMocks
//    private EventFetcher eventFetcher;
//
//    @Test
//    void shouldFetchEventsFromRemoteApi() throws Exception {
//        // Przygotowanie testowych danych
//        EventDto dto = TestUtil.getEventDto();
//        Map<String, EventDto> map = new HashMap<>();
//        map.put("first", dto);
//
//        // Serializacja mapy na JSON
//        String jsonString = "{\"first\":" + new ObjectMapper().writeValueAsString(dto) + "}";
//
//        String url = "https://plan-219ec-default-rtdb.europe-west1.firebasedatabase.app/wydarzenia.json";
//
//        // Refleksyjnie ustaw właściwość firebaseUrl, jeśli używasz @Value w testach
//        ReflectionTestUtils.setField(eventFetcher, "firebaseUrl", url);
//
//        when(restTemplate.getForObject(url, String.class)).thenReturn(jsonString);
//        when(objectMapper.readValue(jsonString, new TypeReference<Map<String, EventDto>>() {}))
//                .thenReturn(map);
//
//        // Wywołanie metody fetcher
//        Optional<List<EventDto>> resultOpt = eventFetcher.fetchEvents();
//        List<EventDto> resultList = resultOpt.get();
//
//        // Assercje
//        assertThat(resultOpt).isNotEmpty();
//        assertThat(resultList).hasSize(1);
//        assertThat(resultList.get(0).getTitle()).isEqualTo(dto.getTitle());
//        assertThat(resultList.get(0).getRoom()).isEqualTo(dto.getRoom());
//        assertThat(resultList.get(0).getRrule().getFreq()).isEqualTo(dto.getRrule().getFreq());
//
//        verify(restTemplate, times(1)).getForObject(url, String.class);
//        verify(objectMapper, times(1))
//                .readValue(jsonString, new com.fasterxml.jackson.core.type.TypeReference<Map<String, EventDto>>() {});
//    }
//}

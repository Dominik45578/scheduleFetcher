package com.polibuda.auth;


import com.polibuda.auth.grpc.AuthGrpcClient;
import com.polibuda.dto.EventDto;
import com.polibuda.dto.FilterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FilteredEventFetcher {

    private final AuthGrpcClient grpcClient;

    public Optional<List<EventDto>> fetchEventsWithFilter(FilterRequestDto filterRequest) {
        try {
            List<EventDto> events = grpcClient.getFilteredEvents(
                    filterRequest.getFaculty(),
                    filterRequest.getGroups()
            );
            return Optional.of(events);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
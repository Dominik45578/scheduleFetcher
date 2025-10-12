package com.polibuda.scraper.service;

import com.polibuda.dto.EventDto;
import com.polibuda.model.FacultyName;
import com.polibuda.model.GroupName;
import com.polibuda.scraper.util.EventFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventFetcher fetcher;
    private final ExtractorService extractor;

    private List<EventDto> cachedEvents;
    private List<FacultyName> cachedFaculties;
    private List<GroupName> cachedGroups;


    public List<EventDto> getEvents() {
        if (cachedEvents != null) {
            return List.copyOf(cachedEvents);
        }
        Optional<List<EventDto>> events = fetcher.fetchEvents();
        events.ifPresent(list -> cachedEvents = list);
        return cachedEvents;
    }

    public void refreshEvents() {
        cachedEvents = null;
        getEvents();
    }

    public  List<FacultyName> getFaculties() {
        if (cachedFaculties != null) {
            return  List.copyOf(cachedFaculties);
        }
        Optional<Set<FacultyName>> events = extractor.getFaculties(getEvents());
        events.ifPresent(list->  cachedFaculties = List.copyOf(list));

        return List.copyOf(cachedFaculties);
    }

    public  List<GroupName> getGroups() {
        if (cachedGroups != null) {
            return  List.copyOf(cachedGroups);
        }
        Optional<Set<GroupName>> events = extractor.getGroups(getEvents());
        events.ifPresent(list->  cachedGroups = List.copyOf(EventFilterUtil.generateIndividualGroupCodes(list)));

        return List.copyOf(cachedGroups);
    }

    public boolean groupExist(String code){
        return cachedGroups.stream().anyMatch(g -> g.getCode().equalsIgnoreCase(code));
    }
    public boolean facultyExist(String code){
        return cachedFaculties.stream().anyMatch(g -> g.getCode().equalsIgnoreCase(code));
    }
    public FacultyName getFacultyFromCode(String code){
        return cachedFaculties.stream().filter(f -> f.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }
    public GroupName getGroupFromCode(String code){
        return cachedGroups.stream().filter(g -> g.getCode().equalsIgnoreCase(code)).findFirst().orElse(null);
    }



}

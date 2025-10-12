package com.polibuda.scraper.service;

import com.polibuda.dto.EventDto;
import com.polibuda.model.FacultyName;
import com.polibuda.model.GroupName;
import com.polibuda.scraper.util.EventFilterUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtractorService {

    public <T> Set<T> getUniqueValueBySet(Function<EventDto,T> supplier, List<EventDto> events){
        return  events.stream()
                .map(supplier)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public <T> Set<T> getUniqueValue(Function<EventDto,T> supplier, List<EventDto> events) {
        return getUniqueValueBySet(supplier, events);
    }


    public Optional<Set<GroupName>> getGroups(List<EventDto> events) {
        Set<GroupName> groupSet = new HashSet<>();
        for(String group : getUniqueValue(EventDto::getGroup, events)) {
            if(group.matches(".*[,/].*")){
                continue;
            }
            groupSet.add(new GroupName(group));
        }
        return  Optional.of(groupSet);
    }

    public Optional<Set<FacultyName>> getFaculties(List<EventDto> events) {
        Set<FacultyName> facultySet = new HashSet<>();
        for(String group : getUniqueValue(EventDto::getFaculty,events)) {
            facultySet.add(new FacultyName(group));
        }
        return  Optional.of(facultySet);
    }

    public List<String> filterGroupByFaculty(List<EventDto> events, FacultyName faculty){
        List<String> groups = new ArrayList<>();
       EventFilterUtil.filterByFaculty(events,faculty).stream()
               .forEach(e -> groups.add(e.getGroup()));
       return groups;
    }



}

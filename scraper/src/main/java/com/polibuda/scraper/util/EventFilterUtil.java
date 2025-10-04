package com.polibuda.scraper.util;

import com.polibuda.scraper.dto.EventDto;
import com.polibuda.scraper.model.FacultyName;
import com.polibuda.scraper.model.GroupName;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventFilterUtil {

    public static List<EventDto> filterByGroup(List<EventDto> events, GroupName groupName) {
        return events.stream()
                     .filter(e -> e.getGroup().equalsIgnoreCase(groupName.getCode()))
                     .toList();
    }

    public static List<EventDto> filterByFaculty(List<EventDto> events, FacultyName facultyName) {
        return events.stream()
                     .filter(e -> e.getFaculty().equalsIgnoreCase(facultyName.getCode()))
                     .toList();
    }


    public static List<String> getGroupCodes(List<GroupName> groups) {
        return groups.stream()
                .map(GroupName::getCode)
                .toList();
    }

    public static List<EventDto> filterByFacultyAndGroups(List<EventDto> events, FacultyName faculty, List<GroupName> groups) {
        List<String> groupCodes = getGroupCodes(groups);
        return filterByFaculty(events, faculty)
                .stream()
                .filter(e -> groupCodes.contains(e.getGroup()))
                .toList();
    }

}

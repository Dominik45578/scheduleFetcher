package com.polibuda.scraper.util;

import com.polibuda.dto.EventDto;
import com.polibuda.model.FacultyName;
import com.polibuda.model.GroupName;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventFilterUtil {

    public static List<EventDto> filterByGroup(List<EventDto> events, GroupName groupName) {
        return events.stream()
                     .filter(e -> e.getGroup().contains(groupName.getCode()))
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
        return filterByFaculty(events, faculty).stream()
                .filter(e -> {
                    List<String> eventGroups = Stream.of(e.getGroup()
                                    .split("/"))
                            .map(String::trim)
                            .map(String::toLowerCase)
                            .toList();

                    return groupCodes.stream()
                            .map(String::toLowerCase)
                            .anyMatch(eventGroups::contains);
                })
                .toList();
    }

    public static Set<GroupName> generateIndividualGroupCodes(Set<GroupName> groups) {
        return groups.stream()
                .map(GroupName::getCode)
                .flatMap(code -> getSplitedGroups(code).stream())
                .map(String::toLowerCase)
                .map(String::trim)
                .filter(group -> !group.isEmpty())
                .map(GroupName::new) // Tworzenie nowego obiektu GroupName z każdego kodu
                .collect(Collectors.toSet());
    }

    public static List<String> getSplitedGroups(String groupCode) {
        if (groupCode == null || groupCode.trim().isEmpty()) {
            return Collections.emptyList();
        }

        return splitByMultipleDelimiters(groupCode);
    }

    private static List<String> splitByMultipleDelimiters(String input) {
        // Regex dla podziału po przecinku i ukośniku: [,/]+
        return Arrays.stream(input.split("[,/]+"))
                .map(String::trim)
                .filter(part -> !part.isEmpty())
                .collect(Collectors.toList());
    }

    private static String normalizeGroupCode(String groupCode) {
        if (groupCode == null || groupCode.trim().isEmpty()) {
            return null;
        }

        return groupCode.trim().toLowerCase();
    }

    // Jeśli GroupName ma bardziej złożony konstruktor
    public static Set<GroupName> generateIndividualGroupCodesComplex(List<GroupName> groups) {
        return groups.stream()
                .flatMap(group -> getSplitedGroups(group.getCode()).stream()
                        .map(String::toLowerCase)
                        .map(String::trim)
                        .filter(code -> !code.isEmpty())
                        .map(code -> createGroupName(code, group))) // Przekazanie oryginalnej grupy dla dodatkowych danych
                .collect(Collectors.toSet());
    }

    private static GroupName createGroupName(String code, GroupName originalGroup) {
        // Jeśli GroupName ma więcej pól, możemy je skopiować z oryginalnej grupy
        GroupName newGroup = new GroupName(code);
        // Jeśli są dodatkowe pola:
        // newGroup.setSomeProperty(originalGroup.getSomeProperty());
        return newGroup;
    }


}

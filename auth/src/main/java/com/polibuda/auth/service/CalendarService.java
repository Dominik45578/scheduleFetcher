package com.polibuda.auth.service;



import com.polibuda.auth.model.CalendarElement;
import com.polibuda.auth.model.CalendarElementRRule;
import com.polibuda.auth.model.CalendarFile;
import com.polibuda.dto.EventDto;
import com.polibuda.dto.RRuleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private static final String PROD_ID = "-//Polibuda Scraper//ICS Generator//PL";
    private static final String VERSION = "2.0";

    public CalendarFile buildCalendarFile(List<EventDto> eventDtos) {
        List<CalendarElement> elements = eventDtos.stream()
                .map(this::buildCalendarElement)
                .collect(Collectors.toList());

        return CalendarFile.builder()
                .prodId(PROD_ID)
                .version(VERSION)
                .events(elements)
                .build();
    }


    private CalendarElement buildCalendarElement(EventDto eventDto) {
        CalendarElement.CalendarElementBuilder builder = CalendarElement.builder()
                .title(eventDto.getTitle())
                .start(eventDto.getStart())
                .end(eventDto.getEnd())
                .duration(eventDto.getDuration())
                .faculty(eventDto.getFaculty())
                .group(eventDto.getGroup())
                .instructor(eventDto.getInstructor())
                .room(eventDto.getRoom());

        if (eventDto.getRrule() != null) {
            builder.rrule(buildRRule(eventDto.getRrule()));
        }

        return builder.build();
    }

    private CalendarElementRRule buildRRule(RRuleDto rruleDto) {
        return CalendarElementRRule.builder()
                .dtstart(rruleDto.getDtstart())
                .freq(rruleDto.getFreq().toUpperCase()) // np. weekly -> WEEKLY
                .interval(rruleDto.getInterval())
                .until(rruleDto.getUntil())
                .build();
    }

}

package com.polibuda.scraper;

import com.polibuda.scraper.dto.EventDto;
import com.polibuda.scraper.dto.RRuleDto;
import lombok.Getter;

@Getter
public class TestUtil {
    
    public static RRuleDto getRRuleDto() {
        RRuleDto rrule = new RRuleDto();
        rrule.setDtstart("2025-10-10T12:00:00Z");
        rrule.setFreq("WEEKLY");
        rrule.setInterval(1);
        rrule.setUntil("2025-12-15T12:00:00Z");
        return rrule;
    }
    
    public static EventDto getEventDto() {
        EventDto dto = new EventDto();
        dto.setDuration("90");
        dto.setEnd("13:30");
        dto.setEndDate("2025-10-10");
        dto.setEventType("Lecture");
        dto.setFaculty("Engineering");
        dto.setGroup("A");
        dto.setInstructor("Prof. Nowak");
        dto.setInterval(1);
        dto.setRoom("A105");
        dto.setRrule(getRRuleDto());
        dto.setStart("12:00");
        dto.setStartDate("2025-10-10");
        dto.setStartTime("12:00");
        dto.setTitle("Mathematics for Engineers");
        return dto;
    }
}

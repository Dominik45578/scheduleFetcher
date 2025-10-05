package com.polibuda.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarElement {
    private String title;
    private String start;    // ISO datetime
    private String end;      // ISO datetime
    private String duration; // "HH:mm"
    private String faculty;
    private String group;
    private String instructor;
    private String room;
    private CalendarElementRRule rrule; // opcjonalne
}

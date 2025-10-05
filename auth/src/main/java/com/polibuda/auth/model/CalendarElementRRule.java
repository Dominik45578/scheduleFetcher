package com.polibuda.auth.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarElementRRule {
    private String dtstart; // ISO datetime
    private String freq;    // DAILY, WEEKLY etc.
    private Integer interval;
    private String until;   // ISO datetime
}

package com.polibuda.scraper.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CalendarFile {
    private String prodId;
    private String version;
    private List<CalendarElement> events;
}

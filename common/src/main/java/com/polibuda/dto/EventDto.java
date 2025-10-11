package com.polibuda.dto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventDto {

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("end")
    private String end;

    @JsonProperty("endDate")
    private String endDate;

    @JsonProperty("eventType")
    private String eventType;

    @JsonProperty("faculty")
    private String faculty;

    @JsonProperty("group")
    private String group;

    @JsonProperty("instructor")
    private String instructor;

    @JsonProperty("interval")
    private Integer interval;

    @JsonProperty("room")
    private String room;

    @JsonProperty("rrule")
    private RRuleDto rrule;

    @JsonProperty("start")
    private String start;

    @JsonProperty("startDate")
    private String startDate;

    @JsonProperty("startTime")
    private String startTime;

    @JsonProperty("title")
    private String title;
}

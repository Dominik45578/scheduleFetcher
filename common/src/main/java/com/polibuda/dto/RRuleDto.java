package com.polibuda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RRuleDto {

    @JsonProperty("dtstart")
    private String dtstart;

    @JsonProperty("freq")
    private String freq;

    @JsonProperty("interval")
    private Integer interval;

    @JsonProperty("until")
    private String until;
}

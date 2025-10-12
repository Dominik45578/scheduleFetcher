package com.polibuda.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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

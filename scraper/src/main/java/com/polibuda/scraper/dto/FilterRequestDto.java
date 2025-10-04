package com.polibuda.scraper.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class FilterRequestDto {
    private String faculty;       // np. "IWIK_S1"
    private List<String> groups;  // np. ["LK1","P1"]
}

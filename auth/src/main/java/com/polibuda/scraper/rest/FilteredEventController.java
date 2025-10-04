package com.polibuda.scraper.rest;

import com.polibuda.scraper.dto.FilterRequestDto;
import com.polibuda.scraper.service.IcsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class FilteredEventController {

    private final IcsService icsService;

    @PostMapping("/ics")
    public String filterEvents(@RequestBody FilterRequestDto request) {
        // Delegacja całej logiki do serwisu
        return icsService.getIcsFile(request);
    }
}

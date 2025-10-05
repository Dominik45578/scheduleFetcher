package com.polibuda.auth.rest;

import com.polibuda.auth.dto.FilterRequestDto;
import com.polibuda.auth.service.IcsService;
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
        return icsService.getIcsFile(request);
    }
}

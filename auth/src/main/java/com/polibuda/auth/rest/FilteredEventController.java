package com.polibuda.auth.rest;

import com.polibuda.auth.dto.FilterRequestDto;
import com.polibuda.auth.model.FacultyName;
import com.polibuda.auth.model.GroupName;
import com.polibuda.auth.service.IcsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
    @GetMapping("/groups")
    public List<String> getGroups() {
        return GroupName.getGroupNames();
    }
    @GetMapping("/faculty")
    public List<String> getFaculty() {
        return FacultyName.getFacultyNames();
    }
}

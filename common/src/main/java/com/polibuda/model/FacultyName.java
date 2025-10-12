package com.polibuda.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class FacultyName {

    private final String code;

    public FacultyName(String code) {
        this.code = code;
    }
}
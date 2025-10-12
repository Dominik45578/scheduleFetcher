package com.polibuda.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class FacultyName {

    private final String code;

    public FacultyName(String code) {
        this.code = code;
    }
}
package com.polibuda.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@EqualsAndHashCode
public class GroupName {

    private final String code;

    public GroupName(String code) {
        this.code = code;
    }
}

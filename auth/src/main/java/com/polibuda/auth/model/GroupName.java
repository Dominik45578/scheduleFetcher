package com.polibuda.auth.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum GroupName {
    LK1("Lk1"),
    LK2("Lk2"),
    LK3("Lk3"),
    LK4("Lk4"),
    LK5("Lk5"),
    LK6("Lk6"),
    LK7("Lk7"),
    LK8("Lk8"),
    LK0("Lk0"),
    P1("P1"),
    P2("P2"),
    P3("P3"),
    P4("P4"),
    P5("P5"),
    P6("P6"),
    L1("L1"),
    L2("L2"),
    L3("L3"),
    L4("L4"),
    L5("L5"),
    L6("L6"),
    L7("L7"),
    L8("L8"),
    L9("L9"),
    L10("L10"),
    L11("L11"),
    L12("L12"),
    LEK1("Lek1"),
    LEK2("Lek2"),
    LEK3("Lek3"),
    LEK4("Lek4"),
    LEK5("Lek5"),
    LEK6("Lek6"),
    LEK("Lek"),
    C1("Ć1"),
    C2("Ć2"),
    C3("Ć3"),
    C4("Ć4"),
    S1("S1"),
    S2("S2"),
    S3("S3"),
    LAB("Lab"),
    LEC("Lec"),
    W("W"),
    WIEIK("WIEiK"),
    WIITCH("WIiTCH"),
    E("E"),
    C("C"),
    K2("k2"),
    DD("dd"),
    AA("aa"),
    SS("ss"),
    P("P"),
    L("L"),
    LK("Lk"),
    EMPTY("");  // pole puste

    private final String code;

    GroupName(String code) {
        this.code = code;
    }

    public static GroupName fromCode(String code) {
        for (GroupName g : values()) {
            if (g.code.equalsIgnoreCase(code)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Nieznany kod grupy: " + code);
    }

    public static List<String> getGroupNames() {
        List<String> groupNames = new ArrayList<>();
        for (GroupName g : values()) {
            groupNames.add(g.code);
        }
        return groupNames;
    }
}

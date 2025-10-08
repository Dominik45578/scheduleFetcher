package com.polibuda.auth.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public enum FacultyName {

    IWIK_S1("IwIKs1"),
    IWIK_S2("IwIKs2"),
    IWIK_S3("IwIKs3"),
    IWIK_S4("IwIKs4"),
    IWIK_S5("IwIKs5"),
    IWIK_S6("IwIKs6"),
    IWIK_S7("IwIKs7"),

    EIA_S1("EiAs1"),
    EIA_S2("EiAs2"),
    EIA_S3("EiAs3"),
    EIA_S4("EiAs4"),
    EIA_S5("EiAs5"),
    EIA_S6("EiAs6"),
    EIA_S7("EiAs7"),

    EIA_S7A("EiAs7A"),
    EIA_S7I("EiAs7I"),

    IT_S2("Its2"),
    EE("EE");

    private final String code;

    FacultyName(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


    public static FacultyName fromCode(String code) {
        for (FacultyName f : values()) {
            if (f.code.equalsIgnoreCase(code)) {
                return f;
            }
        }
        throw new IllegalArgumentException("Nieznany kod wydziału: " + code);
    }

    public static List<String> getFacultyNames() {
        List<String> groupNames = new ArrayList<>();
        for (FacultyName g : values()) {
            groupNames.add(g.code);
        }
        return groupNames;
    }
}

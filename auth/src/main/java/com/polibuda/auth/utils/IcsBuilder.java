package com.polibuda.auth.utils;

import com.polibuda.auth.model.CalendarElement;
import com.polibuda.auth.model.CalendarElementRRule;
import com.polibuda.auth.model.CalendarFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IcsBuilder {

    private static final DateTimeFormatter ICS_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

    public static String buildIcs(CalendarFile calendarFile) {
        StringBuilder sb = new StringBuilder();

        sb.append("BEGIN:VCALENDAR").append("\n");
        sb.append("PRODID:").append(calendarFile.getProdId()).append("\n");
        sb.append("VERSION:").append(calendarFile.getVersion()).append("\n");

        List<CalendarElement> events = calendarFile.getEvents();
        for (CalendarElement event : events) {
            sb.append("BEGIN:VEVENT").append("\n");
            sb.append("SUMMARY:").append(event.getTitle()).append("\n");
            sb.append("DTSTART:").append(formatDateTime(event.getStart())).append("\n");
            sb.append("DTEND:").append(formatDateTime(event.getEnd())).append("\n");

            if (event.getRrule() != null) {
                sb.append("RRULE:");
                CalendarElementRRule rrule = event.getRrule();
                sb.append("FREQ=").append(rrule.getFreq()).append(";");
                if (rrule.getInterval() != null) sb.append("INTERVAL=").append(rrule.getInterval()).append(";");
                if (rrule.getUntil() != null) sb.append("UNTIL=").append(formatDateTime(rrule.getUntil())).append(";");
                sb.append("\n");
            }

            sb.append("LOCATION:").append(event.getRoom()).append("\n");
            sb.append("DESCRIPTION:").append(event.getInstructor()).append(" - ").append(event.getFaculty()).append(" - ").append(event.getGroup()).append("\n");
            sb.append("END:VEVENT").append("\n");
        }

        sb.append("END:VCALENDAR");
        return sb.toString();
    }

    private static String formatDateTime(String isoDateTime) {
        LocalDateTime dt = LocalDateTime.parse(isoDateTime);
        return dt.format(ICS_DATE_TIME);
    }
}

package seedu.address.commons.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

/**
 * Helper functions for parsing and formatting dates
 */
public class DateUtil {
    public static final String DATE_INPUT_FORMAT = "uuuu-MM-dd";
    public static final String DATETIME_INPUT_FORMAT = "uuuu-MM-dd HH:mm";
    public static final String DATETIME_DISPLAY_FORMAT = "dd MMM yyyy, hh:mm a";
    public static final String DATE_DISPLAY_FORMAT = "dd MMM yyyy";

    /**
     * Parses a LocalDate object from a string
     * @return Parsed date time from String, or if unable to parse, returns null
            */
    public static LocalDate parseDate(String date) {
        if (date == null) {
            return null;
        }
        try {
            LocalDate parsedDate = LocalDate.parse(
                date,
                DateTimeFormatter.ofPattern(DATE_INPUT_FORMAT).withResolverStyle(ResolverStyle.STRICT)
            );
            return parsedDate;
        } catch (DateTimeException ex) {
            return null;
        }
    }
    /**
     * Parses a LocalDateTime object from a string
     * @return Parsed date time from String, or if unable to parse, returns null
     */
    public static LocalDateTime parseDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }
        try {
            return LocalDateTime.parse(
                dateTime,
                DateTimeFormatter.ofPattern(DATETIME_INPUT_FORMAT).withResolverStyle(ResolverStyle.STRICT));
        } catch (DateTimeException ex) {
            return null;
        }
    }

    /**
     * Formats a LocalDateTime object into a string for display
     * @return The formatted date time string if successful, or null if unable to format
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        try {
            return dateTime.format(
                DateTimeFormatter.ofPattern(DATETIME_DISPLAY_FORMAT).withResolverStyle(ResolverStyle.STRICT)
            );
        } catch (DateTimeException ex) {
            return null;
        }
    }

    /**
     * Formats a LocalDateTime object into a string for display
     * @return The formatted date time string if successful, or null if unable to format
     */
    public static String formatDate(LocalDate dateTime) {
        if (dateTime == null) {
            return null;
        }
        try {
            return dateTime.format(
                DateTimeFormatter.ofPattern(DATE_DISPLAY_FORMAT).withResolverStyle(ResolverStyle.STRICT)
            );
        } catch (DateTimeException ex) {
            return null;
        }
    }

}

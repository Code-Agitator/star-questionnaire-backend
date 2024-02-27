package pers.star.questionnaire.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class LocalDateUtil {
    public static final ZoneOffset ZONE = ZoneOffset.of("+8");

    public static final DateTimeFormatter DEFAULT_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDateTime now() {
        return LocalDateTime.now(ZONE);
    }

    public static LocalDate nowDate() {
        return LocalDate.now(ZONE);
    }


    public static LocalDateTime ofNow(LocalTime localTime) {
        return LocalDateTime.of(nowDate(), localTime);
    }

    public static LocalDateTime ofNow(LocalDate localDate, LocalTime localTime) {
        return LocalDateTime.of(localDate, localTime);
    }

    public static long millis() {
        return LocalDateTime.now().atZone(ZONE).toInstant().toEpochMilli();
    }

    public static long seconds() {
        return LocalDateTime.now().toEpochSecond(ZONE);
    }

    public static long millisOf(LocalDateTime time) {
        return time.toInstant(ZONE).toEpochMilli();
    }

    public static long millisAfterDays(long days) {
        return LocalDateTime.now().atZone(ZONE).plusDays(days).toInstant().toEpochMilli();
    }

    public static LocalDateTime of(Long since) {
        return new Date(since).toInstant().atZone(ZONE).toLocalDateTime();
    }

    public static String format(LocalDateTime localDateTime) {
        return Optional.ofNullable(localDateTime).map(t -> t.format(DEFAULT_TIME_FORMATTER)).orElse("");
    }

    public static String format(LocalDateTime localDateTime, DateTimeFormatter formatter) {
        return Optional.ofNullable(localDateTime).map(t -> t.format(formatter)).orElse("");
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        return Optional.ofNullable(localDateTime).map(t -> t.format(DateTimeFormatter.ofPattern(pattern))).orElse("");
    }

    public static LocalDateTime startOfToday() {
        return now().withHour(0).withMinute(0).withSecond(0);
    }

}

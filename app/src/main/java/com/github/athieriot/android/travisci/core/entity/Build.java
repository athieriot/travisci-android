package com.github.athieriot.android.travisci.core.entity;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.DurationFieldType;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.io.Serializable;
import java.util.Locale;

import static org.joda.time.PeriodType.forFields;
import static org.joda.time.format.PeriodFormat.wordBased;

public class Build  implements Serializable {

    public static final Integer RESULT_SUCCESS = 0;
    public static final Integer RESULT_FAILURE = 1;

    public static final Integer STATUS_RUNNING = 0;
    public static final Integer STATUS_FINISHED = 1;

    public static final PeriodFormatter DAYS_HOURS_MINUTES_SECONDS_FORMATTER = new PeriodFormatterBuilder()
                        .appendDays().appendSuffix(" day", " days").appendSeparator(", ")
                        .appendHours().appendSuffix(" hour", " hours").appendSeparator(", ")
                        .appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(" and ")
                        .appendSeconds().appendSuffix(" second", " seconds")
                        .toFormatter();

    public static final DurationFieldType[] DAYS_HOURS_MINUTES_SECONDS = new DurationFieldType[] {
                        DurationFieldType.days(),
                        DurationFieldType.hours(),
                        DurationFieldType.minutes(),
                        DurationFieldType.seconds()};


    private Long id;

    private String slug;

    private String description;

    private Long last_build_id;

    private String last_build_number;

    private Integer last_build_status;

    private Integer last_build_result;

    private Long last_build_duration;

    private String last_build_language;

    private DateTime last_build_started_at;

    private DateTime last_build_finished_at;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLast_build_id() {
        return last_build_id;
    }

    public void setLast_build_id(Long last_build_id) {
        this.last_build_id = last_build_id;
    }

    public String getLast_build_number() {
        return last_build_number;
    }

    public void setLast_build_number(String last_build_number) {
        this.last_build_number = last_build_number;
    }

    public Integer getLast_build_status() {
        return last_build_status;
    }

    public void setLast_build_status(Integer last_build_status) {
        this.last_build_status = last_build_status;
    }

    public Integer getLast_build_result() {
        return last_build_result;
    }

    public void setLast_build_result(Integer last_build_result) {
        this.last_build_result = last_build_result;
    }

    public Long getLast_build_duration() {
        return last_build_duration;
    }

    public void setLast_build_duration(Long last_build_duration) {
        this.last_build_duration = last_build_duration;
    }

    public String getLast_build_language() {
        return last_build_language;
    }

    public void setLast_build_language(String last_build_language) {
        this.last_build_language = last_build_language;
    }

    public DateTime getLast_build_started_at() {
        return last_build_started_at;
    }

    public void setLast_build_started_at(DateTime last_build_started_at) {
        this.last_build_started_at = last_build_started_at;
    }

    public DateTime getLast_build_finished_at() {
        return last_build_finished_at;
    }

    public void setLast_build_finished_at(DateTime last_build_finished_at) {
        this.last_build_finished_at = last_build_finished_at;
    }

    public boolean isSuccessful() {
        return last_build_result != null && last_build_result.equals(RESULT_SUCCESS);
    }

    public boolean isFail() {
        return last_build_result != null && last_build_result.equals(RESULT_FAILURE);
    }

    public boolean isRunning() {
        return last_build_status != null && last_build_status.equals(STATUS_RUNNING);
    }

    public boolean isFinished() {
        return last_build_status != null && last_build_status.equals(STATUS_FINISHED);
    }

    public String prettyPrintDuration() {
        String prettyDuration = "-";

        if(last_build_duration != null) {
            Period millisecondsBuildDuration = new Duration(last_build_duration * 1000L).toPeriod();
            millisecondsBuildDuration = millisecondsBuildDuration.normalizedStandard(forFields(DAYS_HOURS_MINUTES_SECONDS));
            prettyDuration = wordBased(Locale.ENGLISH).print(millisecondsBuildDuration);
        }

        return "Duration: " + prettyDuration;
    }

    public String prettyPrintFinished() {
        return prettyPrintFinished(new DateTime());
    }
    public String prettyPrintFinished(DateTime now) {
        String prettyFinished = "-";

        if(last_build_finished_at != null) {
            Period buildPeriod = new Period(last_build_finished_at, now);
            buildPeriod = buildPeriod.normalizedStandard(forFields(DAYS_HOURS_MINUTES_SECONDS));
            prettyFinished = wordBased(Locale.ENGLISH).print(buildPeriod) + " ago";
        }

        return "Finished: " + prettyFinished;
    }
}

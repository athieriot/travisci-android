package com.github.athieriot.android.travisci.ui;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.Build;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter to display a list of traffic items
 */
public class BuildListAdapter extends AlternatingColorListAdapter<Build> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    public static final int SUCCESS_COLOR = Color.rgb(3, 128, 53);
    public static final int FAILURE_COLOR = Color.rgb(204, 0, 0);
    public static final int BUILDING_COLOR = Color.rgb(102, 102, 102);

    /**
     * @param inflater
     * @param items
     */
    public BuildListAdapter(LayoutInflater inflater, List<Build> items) {
        super(R.layout.build_list_item, inflater, items);

        setItems(items);
    }

    /**
     * @param inflater
     */
    public BuildListAdapter(LayoutInflater inflater) {
        this(inflater, null);

    }

    @Override
    public long getItemId(final int position) {
        final String id =  getItem(position).getId();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.b_name, R.id.b_number, R.id.b_duration, R.id.b_finished };
    }

    @Override
    protected void update(int position, Build build) {
        super.update(position, build);

        setText(R.id.b_name, build.getSlug());
        setText(R.id.b_number, getLastBuildNumberText(build.getLast_build_number()));
        setText(R.id.b_duration, getDurationText(build.getLast_build_duration()));
        setText(R.id.b_finished, getFinishedText(build.getLast_build_started_at(), build.getLast_build_finished_at()));

        if (build.isSuccessful()) {
            this.setTextColor(view, R.id.b_name, SUCCESS_COLOR);
            this.setTextColor(view, R.id.b_number, SUCCESS_COLOR);
        } else if (build.isFail()) {
            this.setTextColor(view, R.id.b_name, FAILURE_COLOR);
            this.setTextColor(view, R.id.b_number, FAILURE_COLOR);
        } else {
            this.setTextColor(view, R.id.b_name, BUILDING_COLOR);
            this.setTextColor(view, R.id.b_number, BUILDING_COLOR);
        }
    }

    private String getLastBuildNumberText(String lastBuildId) {
        return lastBuildId == null ? "#" : lastBuildId;
    }

    private String getDurationText(Integer duration) {
        return "Duration: " + (duration == null ? "-" : getPeriodFormatter().print(new Period(duration * 100)));
    }

    private String getFinishedText(String startDate, String finishedDate) {
        DateTime start = DateTime.parse(startDate);
        DateTime end = finishedDate == null ? null : DateTime.parse(finishedDate);
        return "Finished: " + (end == null ? "-" : getPeriodFormatter().print(new Period(start, end)) + " ago");
    }

    /**
     * Set text on text view with given id
     *
     * @param parentView
     * @param childViewId
     * @param color
     * @return text view
     */
    protected TextView setTextColor(final View parentView, final int childViewId,
                               final int color) {
        final TextView textView = textView(parentView, childViewId);
        textView.setTextColor(color);
        return textView;
    }

    private PeriodFormatter getPeriodFormatter() {
        return new PeriodFormatterBuilder()
                .appendDays()
                .appendSuffix(" day", " days")
                .appendSeparator(" and ")
                .appendHours()
                .appendSuffix(" hour", " hours")
                .appendSeparator(" and ")
                .appendMinutes()
                .appendSuffix(" minute", " minutes")
                .appendSeparator(" and ")
                .appendSeconds()
                .appendSuffix(" second", " seconds")
                .toFormatter();
    }
}

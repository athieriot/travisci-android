package com.github.athieriot.android.travisci.ui.Repository;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.entity.Repository;
import com.github.athieriot.android.travisci.ui.AlternatingColorListAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter to display a list of traffic items
 */
public class RepositoryListAdapter extends AlternatingColorListAdapter<Repository> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    public static final int SUCCESS_COLOR = Color.rgb(3, 128, 53);
    public static final int FAILURE_COLOR = Color.rgb(204, 0, 0);
    public static final int BUILDING_COLOR = Color.rgb(102, 102, 102);

    /**
     * @param inflater
     * @param items
     */
    public RepositoryListAdapter(LayoutInflater inflater, List<Repository> items) {
        super(R.layout.repository_list_item, inflater, items);

        setItems(items);
    }

    @Override
    public long getItemId(final int position) {
        final String id =  getItem(position).getId().toString();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[] { R.id.r_name, R.id.r_number, R.id.r_duration, R.id.r_finished };
    }

    @Override
    protected void update(int position, Repository repository) {
        super.update(position, repository);

        setText(R.id.r_name, repository.getSlug());
        setText(R.id.r_number, repository.getLast_build_number());
        setText(R.id.r_duration, repository.prettyPrintDuration());
        setText(R.id.r_finished, repository.prettyPrintFinished());

        if (repository.isSuccessful()) {
            colorTextViews(SUCCESS_COLOR);
        } else if (repository.isFail()) {
            colorTextViews(FAILURE_COLOR);
        } else {
            colorTextViews(BUILDING_COLOR);
        }
    }

    private void colorTextViews(int color) {
        this.setTextColor(view, R.id.r_name, color);
        this.setTextColor(view, R.id.r_number, color);
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
}

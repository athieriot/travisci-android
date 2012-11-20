package com.github.athieriot.android.travisci.ui.Workers;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.entity.Repository;
import com.github.athieriot.android.travisci.core.entity.Worker;
import com.github.athieriot.android.travisci.ui.AlternatingColorListAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter to display a list of traffic items
 */
public class WorkerListAdapter extends AlternatingColorListAdapter<Worker> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    public static final int SUCCESS_COLOR = Color.rgb(3, 128, 53);
    public static final int FAILURE_COLOR = Color.rgb(204, 0, 0);
    public static final int BUILDING_COLOR = Color.rgb(102, 102, 102);

    /**
     * @param inflater
     * @param items
     */
    public WorkerListAdapter(LayoutInflater inflater, List<Worker> items) {
        super(R.layout.worker_list_item, inflater, items);

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
        return new int[] { R.id.w_name, R.id.w_state, R.id.w_r_name, R.id.w_r_number, R.id.w_j_branch };
    }

    @Override
    protected void update(int position, Worker worker) {
        super.update(position, worker);

        setText(R.id.w_name, worker.getName());
        setText(R.id.w_state, worker.getState());

        Repository repository = worker.getPayload() == null ? null : worker.getPayload().getRepository();
        if(worker.getPayload() != null && repository != null) {
            setText(R.id.w_r_name, repository.getSlug());
            setText(R.id.w_r_number, repository.getLast_build_number());

            if(worker.getPayload().getJob() != null) {
                setText(R.id.w_j_branch, "Branch: " + worker.getPayload().getJob().getBranch());
            }

            if (repository.isSuccessful()) {
                colorTextViews(SUCCESS_COLOR);
            } else if (worker.getPayload().getRepository().isFail()) {
                colorTextViews(FAILURE_COLOR);
            } else {
                colorTextViews(BUILDING_COLOR);
            }
        } else {
            setText(R.id.w_r_name, "-");
            setText(R.id.w_r_number, "#");

            colorTextViews(BUILDING_COLOR);
        }

    }

    private void colorTextViews(int color) {
        this.setTextColor(view, R.id.w_r_name, color);
        this.setTextColor(view, R.id.w_r_number, color);
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

package com.github.athieriot.android.travisci.ui;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.Build;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Adapter to display a list of traffic items
 */
public class BuildListAdapter extends AlternatingColorListAdapter<Build> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

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
        return new int[] { R.id.b_name };
    }

    @Override
    protected void update(int position, Build build) {
        super.update(position, build);

        setText(R.id.b_name, build.getSlug());

        if (build.isSuccessful()) {
            setTextColor(R.id.b_name, Color.rgb(3, 128, 53));
        } else if (build.isFail()) {
            setTextColor(R.id.b_name, Color.rgb(204, 0, 0));
        } else {
            setTextColor(R.id.b_name, Color.rgb(102, 102, 102));
        }
    }
}

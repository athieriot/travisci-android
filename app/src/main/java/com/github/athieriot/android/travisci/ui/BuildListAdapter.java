package com.github.athieriot.android.travisci.ui;

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
public class BuildListAdapter extends SingleTypeAdapter<Build> {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd");

    /**
     * @param inflater
     * @param items
     */
    public BuildListAdapter(LayoutInflater inflater, List<Build> items) {
        super(inflater, R.layout.build_list_item);

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

        setText(R.id.b_name, build.getSlug());
    }
}

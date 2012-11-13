
package com.github.athieriot.android.travisci.ui;

import android.view.LayoutInflater;
import com.actionbarsherlock.R.color;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.R.drawable;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import java.util.List;

/**
 * List adapter that colors rows in alternating colors
 *
 * @param <V>
 */
public abstract class AlternatingColorListAdapter<V> extends
        SingleTypeAdapter<V> {

    private final int primaryResource;

    private final int secondaryResource;

    /**
     * Create adapter with alternating row colors
     *
     * @param layoutId
     * @param inflater
     * @param items
     */
    public AlternatingColorListAdapter(final int layoutId,
            final LayoutInflater inflater, final List<V> items) {
        this(layoutId, inflater, items, true);
    }

    /**
     * Create adapter with alternating row colors
     *
     * @param layoutId
     * @param inflater
     * @param items
     * @param selectable
     */
    public AlternatingColorListAdapter(final int layoutId,
            LayoutInflater inflater, final List<V> items, boolean selectable) {
        super(inflater, layoutId);

        primaryResource = R.color.white;
        secondaryResource = R.color.travis_background_grey;

        setItems(items);
    }

    @Override
    protected void update(final int position, final V item) {
        if (position % 2 != 0)
            view.setBackgroundResource(primaryResource);
        else
            view.setBackgroundResource(secondaryResource);
    }
}

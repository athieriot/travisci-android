package com.github.athieriot.android.travisci.ui.Workers;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import com.github.athieriot.android.travisci.BootstrapServiceProvider;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.entity.Worker;
import com.github.athieriot.android.travisci.ui.ItemListFragment;
import com.github.athieriot.android.travisci.ui.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.List;

import static com.github.athieriot.android.travisci.core.Constants.Extra.WORKER;

public class WorkerListFragment extends ItemListFragment<Worker> {

    @Inject private BootstrapServiceProvider serviceProvider;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_worker);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
    }

    @Override
    public Loader<List<Worker>> onCreateLoader(int id, Bundle args) {
        final List<Worker> initialItems = items;
        return new ThrowableLoader<List<Worker>>(getActivity(), items) {
            @Override
            public List<Worker> loadData() throws Exception {

                try {
                    List<Worker> lastest = serviceProvider.getService().getWorkers();
                    if (lastest != null)
                        return lastest;
                    else
                        return Collections.emptyList();
                } catch (OperationCanceledException e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };

    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        Worker worker = ((Worker) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), WorkerActivity.class).putExtra(WORKER, worker));
    }

    @Override
    public void onLoadFinished(Loader<List<Worker>> loader, List<Worker> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_workers;
    }

    @Override
    protected SingleTypeAdapter<Worker> createAdapter(List<Worker> items) {
        return new WorkerListAdapter(getActivity().getLayoutInflater(), items);
    }
}

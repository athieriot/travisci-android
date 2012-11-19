package com.github.athieriot.android.travisci.ui.Repository;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import com.github.athieriot.android.travisci.BootstrapServiceProvider;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.entity.Repository;
import com.github.athieriot.android.travisci.ui.ItemListFragment;
import com.github.athieriot.android.travisci.ui.ThrowableLoader;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.google.inject.Inject;

import java.util.Collections;
import java.util.List;

import static com.github.athieriot.android.travisci.core.Constants.Extra.REPOSITORY;

public class RepositoryListFragment extends ItemListFragment<Repository> {

    @Inject private BootstrapServiceProvider serviceProvider;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_repository);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
    }



    @Override
    public Loader<List<Repository>> onCreateLoader(int id, Bundle args) {
        final List<Repository> initialItems = items;
        return new ThrowableLoader<List<Repository>>(getActivity(), items) {
            @Override
            public List<Repository> loadData() throws Exception {

                try {
                    List<Repository> lastest = serviceProvider.getService().getRepositories();
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
        Repository repository = ((Repository) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), RepositoryActivity.class).putExtra(REPOSITORY, repository));
    }

    @Override
    public void onLoadFinished(Loader<List<Repository>> loader, List<Repository> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_repositories;
    }

    @Override
    protected SingleTypeAdapter<Repository> createAdapter(List<Repository> items) {
        return new RepositoryListAdapter(getActivity().getLayoutInflater(), items);
    }
}

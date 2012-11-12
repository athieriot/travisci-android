package com.github.athieriot.android.travisci.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;
import com.github.athieriot.android.travisci.BootstrapServiceProvider;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.AvatarLoader;
import com.github.athieriot.android.travisci.core.Build;
import com.github.athieriot.android.travisci.core.Constants;
import com.github.athieriot.android.travisci.core.User;
import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.google.inject.Inject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static com.github.athieriot.android.travisci.core.Constants.Extra.BUILD;
import static com.github.athieriot.android.travisci.core.Constants.Extra.USER;
import static com.github.athieriot.android.travisci.core.Constants.Http.URL_USERS;

public class BuildListFragment extends ItemListFragment<Build> {

    @Inject private BootstrapServiceProvider serviceProvider;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_builds);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
    }



    @Override
    public Loader<List<Build>> onCreateLoader(int id, Bundle args) {
        final List<Build> initialItems = items;
        return new ThrowableLoader<List<Build>>(getActivity(), items) {
            @Override
            public List<Build> loadData() throws Exception {

                try {
                    List<Build> lastest = serviceProvider.getService().getBuilds();
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
        Build build = ((Build) l.getItemAtPosition(position));

        startActivity(new Intent(getActivity(), BuildActivity.class).putExtra(BUILD, build));
    }

    @Override
    public void onLoadFinished(Loader<List<Build>> loader, List<Build> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_builds;
    }

    @Override
    protected SingleTypeAdapter<Build> createAdapter(List<Build> items) {
        return new BuildListAdapter(getActivity().getLayoutInflater(), items);
    }
}



package com.github.athieriot.android.travisci.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.ui.Repository.RepositoryListFragment;
import com.github.athieriot.android.travisci.ui.Workers.WorkerListFragment;

/**
 * Pager adapter
 */
public class BootstrapPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    /**
     * Create pager adapter
     *
     * @param resources
     * @param fragmentManager
     */
    public BootstrapPagerAdapter(Resources resources, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
        case 0:
            RepositoryListFragment repositoriesFragment = new RepositoryListFragment();
            repositoriesFragment.setArguments(bundle);
            return repositoriesFragment;
        case 1:
            WorkerListFragment workerListFragment = new WorkerListFragment();
            workerListFragment.setArguments(bundle);
            return workerListFragment;
        default:
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
        case 0:
            return resources.getString(R.string.page_repos);
        case 1:
            return resources.getString(R.string.page_workers);
        default:
            return null;
        }
    }
}

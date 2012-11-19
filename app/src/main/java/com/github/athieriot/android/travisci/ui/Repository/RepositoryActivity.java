package com.github.athieriot.android.travisci.ui.Repository;

import android.os.Bundle;
import android.widget.TextView;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.entity.Repository;
import com.github.athieriot.android.travisci.ui.BootstrapActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import static com.github.athieriot.android.travisci.core.Constants.Extra.REPOSITORY;

public class RepositoryActivity extends BootstrapActivity {

    @InjectExtra(REPOSITORY) protected Repository repository;

    @InjectView(R.id.r_name) protected TextView name;
    @InjectView(R.id.r_description) protected TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.repository_view);

        name.setText(repository.getSlug());
        description.setText(repository.getDescription());
    }
}

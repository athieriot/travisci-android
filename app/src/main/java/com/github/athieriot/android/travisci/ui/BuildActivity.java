package com.github.athieriot.android.travisci.ui;

import android.os.Bundle;
import android.widget.TextView;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.Build;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;
import static com.github.athieriot.android.travisci.core.Constants.Extra.BUILD;

public class BuildActivity extends BootstrapActivity {

    @InjectExtra(BUILD) protected Build build;

    @InjectView(R.id.b_name) protected TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.build_view);

        name.setText(build.getSlug());
    }
}

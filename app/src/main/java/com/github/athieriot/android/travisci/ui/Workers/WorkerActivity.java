package com.github.athieriot.android.travisci.ui.Workers;

import android.os.Bundle;
import android.widget.TextView;
import com.github.athieriot.android.travisci.R;
import com.github.athieriot.android.travisci.core.entity.Worker;
import com.github.athieriot.android.travisci.ui.BootstrapActivity;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import static com.github.athieriot.android.travisci.core.Constants.Extra.WORKER;

public class WorkerActivity extends BootstrapActivity {

    @InjectExtra(WORKER) protected Worker worker;

    @InjectView(R.id.w_name) protected TextView name;
    @InjectView(R.id.w_host) protected TextView host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.worker_view);

        name.setText(worker.getName());
        host.setText(worker.getHost());
    }
}

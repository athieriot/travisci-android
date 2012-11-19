
package com.github.athieriot.android.travisci;

import android.accounts.AccountsException;
import com.github.athieriot.android.travisci.core.BootstrapService;
import com.github.athieriot.android.travisci.core.UserAgentProvider;
import com.google.inject.Inject;

import java.io.IOException;

/**
 * Provider for a {@link com.github.athieriot.android.travisci.core.BootstrapService} instance
 */
public class BootstrapServiceProvider {

//    @Inject private ApiKeyProvider keyProvider;
    @Inject private UserAgentProvider userAgentProvider;

    /**
     * Get service for configured key provider
     * <p>
     * This method gets an auth key and so it blocks and shouldn't be called on the main thread.
     *
     * @return bootstrap service
     * @throws IOException
     * @throws AccountsException
     */
    public BootstrapService getService() throws IOException, AccountsException {
        return new BootstrapService(userAgentProvider);
    }
}

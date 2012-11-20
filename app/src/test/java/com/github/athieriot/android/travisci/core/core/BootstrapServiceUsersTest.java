

package com.github.athieriot.android.travisci.core.core;

import com.github.athieriot.android.travisci.core.BootstrapService;
import com.github.athieriot.android.travisci.core.UserAgentProvider;
import com.github.athieriot.android.travisci.core.entity.User;
import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;

/**
 * Unit tests of {@link com.github.athieriot.android.travisci.core.BootstrapService}
 */
@RunWith(MockitoJUnitRunner.class)
public class BootstrapServiceUsersTest {

    /**
     * Create reader for string
     *
     * @param value
     * @return input stream reader
     * @throws IOException
     */
    private static BufferedReader createReader(String value) throws IOException {
        return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(
                value.getBytes(HttpRequest.CHARSET_UTF8))));
    }

    @Mock
    private HttpRequest request;

    private BootstrapService service;

    /**
     * Set up default mocks
     *
     * @throws IOException
     */
    @Before
    public void before() throws IOException {
        service = new BootstrapService(new UserAgentProvider()) {
            protected HttpRequest execute(HttpRequest request) throws IOException {
                return BootstrapServiceUsersTest.this.request;
            }
        };
        doReturn(true).when(request).ok();
    }

    /**
     * Verify getting users with an empty response
     *
     * @throws IOException
     */
    @Test
    public void getUsersEmptyResponse() throws IOException {
        doReturn(createReader("")).when(request).bufferedReader();
        List<User> users = service.getUsers();
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }
}

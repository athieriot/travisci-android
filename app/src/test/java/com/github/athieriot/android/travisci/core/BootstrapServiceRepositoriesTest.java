

package com.github.athieriot.android.travisci.core;

import com.github.athieriot.android.travisci.core.entity.Repository;
import com.github.kevinsawicki.http.HttpRequest;
import org.joda.time.DateTime;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

/**
 * Unit tests of {@link com.github.athieriot.android.travisci.core.BootstrapService}
 */
@RunWith(MockitoJUnitRunner.class)
public class BootstrapServiceRepositoriesTest {

    /**
     * Create reader for string
     *
     * @param value
     * @return input stream reader
     * @throws java.io.IOException
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
     * @throws java.io.IOException
     */
    @Before
    public void before() throws IOException {
        service = new BootstrapService(new UserAgentProvider()) {
            protected HttpRequest execute(HttpRequest request) throws IOException {
                return BootstrapServiceRepositoriesTest.this.request;
            }
        };
        doReturn(true).when(request).ok();
    }

    /**
     * Verify getting news with an empty response
     *
     * @throws java.io.IOException
     */
    @Test
    public void getReposEmptyResponse() throws IOException {
        doReturn(createReader("")).when(request).bufferedReader();
        List<Repository> content = service.getRepositories();
        assertNotNull(content);
        assertTrue(content.isEmpty());
    }

    @Test
    public void getParsedReposResponse() throws IOException {
        doReturn(createReader("[{" +
                "    \"id\": 186118," +
                "    \"slug\": \"athieriot/JTaches\"," +
                "    \"description\": \"JTaches provides a Java way to execute tasks on file events. Mostly inspired by the Guard ruby gem \"," +
                "    \"last_build_id\": 3049849," +
                "    \"last_build_number\": \"90\"," +
                "    \"last_build_status\": 1," +
                "    \"last_build_result\": 0," +
                "    \"last_build_duration\": 117," +
                "    \"last_build_language\": null," +
                "    \"last_build_started_at\": \"2012-11-03T22:07:13Z\"," +
                "    \"last_build_finished_at\": \"2012-11-03T22:09:31Z\"" +
                "}]")
        ).when(request).bufferedReader();

        List<Repository> repositories = service.getRepositories();
        assertNotNull(repositories);
        assertFalse(repositories.isEmpty());
        Repository repository = repositories.get(0);

        assertEquals(186118L, repository.getId().longValue());
        assertEquals("athieriot/JTaches", repository.getSlug());
        assertEquals("JTaches provides a Java way to execute tasks on file events. Mostly inspired by the Guard ruby gem ", repository.getDescription());
        assertEquals(3049849L, repository.getLast_build_id().longValue());
        assertEquals("90", repository.getLast_build_number());
        assertEquals(1, repository.getLast_build_status().intValue());
        assertTrue(repository.isFinished());
        assertEquals(0, repository.getLast_build_result().intValue());
        assertTrue(repository.isSuccessful());
        assertEquals(117L, repository.getLast_build_duration().longValue());
        assertNull(repository.getLast_build_language());
        assertEquals(new DateTime("2012-11-03T22:07:13Z"), repository.getLast_build_started_at());
        assertEquals(new DateTime("2012-11-03T22:09:31Z"), repository.getLast_build_finished_at());
    }

    @Test
    public void getParsedReposPrettyPrints() throws IOException {
        doReturn(createReader("[{" +
                "    \"last_build_duration\": 117," +
                "    \"last_build_started_at\": \"2012-11-03T22:07:13Z\"," +
                "    \"last_build_finished_at\": \"2012-11-03T22:09:31Z\"" +
                "}]")
        ).when(request).bufferedReader();

        List<Repository> repositories = service.getRepositories();
        assertNotNull(repositories);
        assertFalse(repositories.isEmpty());
        Repository repository = repositories.get(0);

        assertEquals("Duration: 1 minute and 57 seconds", repository.prettyPrintDuration());
        assertEquals("Finished: 13 days, 13 hours, 50 minutes and 29 seconds ago", repository.prettyPrintFinished(new DateTime("2012-11-17T12:00:00Z")));
    }

    @Test
    public void getParsedReposPrettyPrintsNull() throws IOException {
        doReturn(createReader("[{}]")
        ).when(request).bufferedReader();

        List<Repository> repositories = service.getRepositories();
        assertNotNull(repositories);
        assertFalse(repositories.isEmpty());
        Repository repository = repositories.get(0);

        assertEquals("Duration: -", repository.prettyPrintDuration());
        assertEquals("Finished: -", repository.prettyPrintFinished(new DateTime("2012-11-17T12:00:00Z")));
    }

    @Test
    public void getParsedReposResultAndStatus() throws IOException {
        doReturn(createReader("[{" +
                "    \"last_build_status\": 0" +
                "}]")
        ).when(request).bufferedReader();

        List<Repository> repositories = service.getRepositories();
        assertNotNull(repositories);
        assertFalse(repositories.isEmpty());
        Repository repository = repositories.get(0);

        assertTrue(repository.isRunning());
        assertFalse(repository.isFail());
        assertFalse(repository.isSuccessful());
    }
}



package com.github.athieriot.android.travisci.core.core;

import com.github.athieriot.android.travisci.core.BootstrapService;
import com.github.athieriot.android.travisci.core.UserAgentProvider;
import com.github.athieriot.android.travisci.core.entity.Worker;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;

/**
 * Unit tests of {@link com.github.athieriot.android.travisci.core.BootstrapService}
 */
@RunWith(MockitoJUnitRunner.class)
public class BootstrapServiceWorkersTest {

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
                return BootstrapServiceWorkersTest.this.request;
            }
        };
        doReturn(true).when(request).ok();
    }

    /**
     * Verify getting workers with an empty response
     *
     * @throws java.io.IOException
     */
    @Test
    public void getWorkersEmptyResponse() throws IOException {
        doReturn(createReader("[{" +
                "        \"id\": 188629," +
                "        \"name\": \"php-4\"," +
                "        \"host\": \"ppp3.worker.travis-ci.org\"," +
                "        \"state\": \"working\"," +
                "        \"last_seen_at\": \"2012-11-20T19:43:24Z\"," +
                "        \"payload\": {" +
                "            \"type\": \"test\"," +
                "            \"build\": {" +
                "                \"id\": 3282139," +
                "                \"number\": \"41.12\"," +
                "                \"commit\": \"a476eaae525e46f2bd7447dc2709589ebe502620\"," +
                "                \"branch\": \"master\"," +
                "                \"ref\": \"refs/pull/33/merge\"," +
                "                \"state\": \"created\"," +
                "                \"pull_request\": 33" +
                "            }," +
                "            \"job\": {" +
                "                \"id\": 3282139," +
                "                \"number\": \"41.12\"," +
                "                \"commit\": \"a476eaae525e46f2bd7447dc2709589ebe502620\"," +
                "                \"branch\": \"master\"," +
                "                \"ref\": \"refs/pull/33/merge\"," +
                "                \"state\": \"created\"," +
                "                \"pull_request\": 33" +
                "            }," +
                "            \"repository\": {" +
                "                \"id\": 322947," +
                "                \"slug\": \"vcstools/vcstools\"," +
                "                \"source_url\": \"git://github.com/vcstools/vcstools.git\"," +
                "                \"last_build_id\": 3282127," +
                "                \"last_build_number\": \"41\"," +
                "                \"last_build_started_at\": \"2012-11-20T19:15:31Z\"," +
                "                \"last_build_finished_at\": null," +
                "                \"last_build_duration\": null," +
                "                \"last_build_result\": null," +
                "                \"description\": \"\"" +
                "            }," +
                "            \"config\": {" +
                "                \"language\": \"python\"," +
                "                \"python\": \"3.2\"," +
                "                \"env\": [" +
                "                    \"HG='Mercurial<=1.9.1' BZR='bzr<=2.4.1' GIT=v1.7.5.4\"" +
                "                ]," +
                "                \"matrix\": {" +
                "                    \"exclude\": [" +
                "                        {" +
                "                            \"python\": 3.2," +
                "                            \"env\": \"HG='Mercurial<=1.4.2' BZR='bzr<=2.0.4' GIT=v1.7.0.4\"" +
                "                        }," +
                "                        {" +
                "                            \"python\": 3.2," +
                "                            \"env\": \"HG=Mercurial<=1.6.3 BZR=bzr<=2.3.4 GIT=v1.7.4.1\"" +
                "                        }," +
                "                        {" +
                "                            \"python\": 3.2," +
                "                            \"env\": \"HG='Mercurial<=1.7.5'\"" +
                "                        }," +
                "                        {" +
                "                            \"python\": 3.2," +
                "                            \"env\": \"HG=Mercurial<=1.9.1\"" +
                "                        }" +
                "                    ]" +
                "                }," +
                "                \"install\": [" +
                "                    \"python setup.py build\"," +
                "                    \"pip install pyyaml\"," +
                "                    \"pip install coverage\"," +
                "                    \"pip install python-dateutil\"," +
                "                    \"pip install $HG\"," +
                "                    \"pip install $BZR\"," +
                "                    \"git clone git://git.kernel.org/pub/scm/git/git.git && cd git && git checkout $GIT && make prefix=/usr/local all && sudo make prefix=/usr/local install\"," +
                "                    \"hg --version\"," +
                "                    \"bzr --version\"," +
                "                    \"git --version\"," +
                "                    \"git config --global user.email \\\"foo@example.com\\\"\"," +
                "                    \"git config --global user.name \\\"Foo Bar\\\"\"," +
                "                    \"echo -e \\\"[ui]\\\\username = Your Name <your@mail.com>\\\" >> ~/.hgrc\"," +
                "                    \"bzr whoami \\\"Your Name <name@example.com>\\\"\"" +
                "                ]," +
                "                \"script\": [" +
                "                    \"nosetests --with-coverage --cover-package vcstools\"" +
                "                ]," +
                "                \"notifications\": {" +
                "                    \"email\": false" +
                "                }," +
                "                \".result\": \"configured\"" +
                "            }," +
                "            \"queue\": \"builds.php\"" +
                "        }," +
                "        \"last_error\": null" +
                "    }]")).when(request).bufferedReader();
        List<Worker> workers = service.getWorkers();
        assertNotNull(workers);
        assertFalse(workers.isEmpty());
        Worker worker = workers.get(0);

        assertEquals("working", worker.getState());
        assertNotNull(worker.getPayload());
        assertNotNull(worker.getPayload().getRepository());
        assertNotNull(worker.getPayload().getJob());
        assertEquals("a476eaae525e46f2bd7447dc2709589ebe502620", worker.getPayload().getJob().getCommit());
        assertNotNull(worker.getPayload().getBuild());
        assertEquals("33", worker.getPayload().getBuild().getPull_request());
//        assertFalse(worker.getPayload().getConfig().getNotifications().isEmail());
//        assertEquals(1, worker.getPayload().getConfig().getScript().size());
//        assertEquals("nosetests --with-coverage --cover-package vcstools", worker.getPayload().getConfig().getScript().get(0));
    }
}

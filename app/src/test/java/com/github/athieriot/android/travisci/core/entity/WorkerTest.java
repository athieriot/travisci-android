package com.github.athieriot.android.travisci.core.entity;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class WorkerTest {

    @Test
    public void testCompareToShowRecentWorkerFirst() throws Exception {
        Worker workerBefore = new Worker();
        Payload payloadBefore = new Payload();
        Repository repositoryBefore = new Repository();
        repositoryBefore.setLast_build_started_at(new DateTime());
        payloadBefore.setRepository(repositoryBefore);
        workerBefore.setPayload(payloadBefore);

        Worker workerAfter = new Worker();
        Payload payloadAfter = new Payload();
        Repository repositoryAfter = new Repository();
        repositoryAfter.setLast_build_started_at(new DateTime().plus(200));
        payloadAfter.setRepository(repositoryAfter);
        workerAfter.setPayload(payloadAfter);

        assertTrue(workerAfter.compareTo(workerBefore) < 0);
    }

    @Test
    public void testCompareToShowNonNullWorkerFirst() throws Exception {
        Worker worker = new Worker();
        Payload payload = new Payload();
        Repository repository = new Repository();
        repository.setLast_build_started_at(new DateTime());
        payload.setRepository(repository);
        worker.setPayload(payload);

        Worker nullWorker = new Worker();

        assertTrue(worker.compareTo(nullWorker) < 0);
    }
}

package com.revolut.transfer.application;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ThreadHandlerTest {

    @InjectMocks
    private ThreadHandler threadHandler;

    @Spy
    private Locks locks;

    private int counter = 0;
    private static final int THREAD_COUNT = 10;
    private static final BigInteger accountID1 = BigInteger.ZERO;
    private static final BigInteger accountID2 = BigInteger.ONE;


    @Test
    public void shouldRunTasksOneAtATimeWhenAccountIDsAreSame() {
        final List<Thread> threadList = getThreadList();

        runThreads(threadList);

        assertCounterValid();
    }

    private List<Thread> getThreadList() {
        final List<Thread> threadList = new ArrayList<>();
        threadList.addAll(getThreadsThatManageAccountIDs(accountID1, accountID2, THREAD_COUNT / 2));
        threadList.addAll(getThreadsThatManageAccountIDs(accountID2, accountID1, THREAD_COUNT / 2));
        return threadList;
    }

    private List<Thread> getThreadsThatManageAccountIDs(final BigInteger account1, final BigInteger account2, final int noThreads) {
        final List<Thread> threadList = new ArrayList<>(noThreads);
        for (int i = 0; i < noThreads; i++) {
            threadList.add(new Thread(() -> threadHandler.manageThreads(account1, account2, () -> counter++)));
        }
        return threadList;
    }

    private void runThreads(final List<Thread> threadList) {
        final ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
        threadList.forEach(service::submit);
        service.shutdown();
        try {
            service.awaitTermination(5, TimeUnit.SECONDS);
        } catch (Exception e) {

        }
    }

    private void assertCounterValid() {
        if (THREAD_COUNT % 2 == 0) {
            assertEquals(THREAD_COUNT, counter);
        } else {
            assertEquals(THREAD_COUNT - 1, counter);
        }
    }
}

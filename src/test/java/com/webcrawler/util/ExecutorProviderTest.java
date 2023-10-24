package com.webcrawler.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.webcrawler.services.ExecutorProvider;

@RunWith(MockitoJUnitRunner.class)
public class ExecutorProviderTest {
    private ExecutorProvider executorProvider;

    @Before
    public void setUp() {
        executorProvider = ExecutorProvider.getInstance();
    }

    /**
     * Test to make sure executor provider is initialised.
     */
    @Test
    public void testExecutorProviderInstanceInitSuccess() {
        Assertions.assertNotNull(executorProvider);
    }

    /**
     * Test to make sure that singleton pattern is followed.
     */
    @Test
    public void testExecutorProviderReusesSameInstance() {
        var nextExecutorProvider = ExecutorProvider.getInstance();
        Assertions.assertEquals(executorProvider, nextExecutorProvider);
    }
}

package util;



import helper.CompletableFutureHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class CompletableFutureHelperTest {
    @Test
    public void testSuccessFulExecutionWithSupplyAsync() {
        var successfulFutures = Arrays.asList(
                CompletableFuture.supplyAsync(() -> 1),
                CompletableFuture.supplyAsync(() -> 2),
                CompletableFuture.supplyAsync(() -> 3)
        );

        var output = CompletableFutureHelper.executeAllFutures(successfulFutures);
        assertTrue(output instanceof ArrayList);
        assertEquals(Arrays.asList(1, 2, 3), output);
    }

    @Test
    public void testSuccessFulExecutionWithRunAsync() {
        var list = new ArrayList<>();
        var successfulFutures = Arrays.asList(
                CompletableFuture.runAsync(() -> {
                    list.add(1);
                }),
                CompletableFuture.runAsync(() -> {
                    list.add(2);
                }),
                CompletableFuture.runAsync(() -> {
                    list.add(3);
                })
        );
        CompletableFutureHelper.executeAllFutures(successfulFutures);
        assertTrue(list.containsAll(Arrays.asList(1, 2, 3)));
    }

    @Test(expected = RuntimeException.class)
    public void testFailedExecution() {
        var successfulFutures = Arrays.asList(
                CompletableFuture.runAsync(() -> {
                    throw new RuntimeException();
                }),
                CompletableFuture.runAsync(() -> {
                }),
                CompletableFuture.runAsync(() -> {
                })
        );

        CompletableFutureHelper.executeAllFutures(successfulFutures);
    }
}

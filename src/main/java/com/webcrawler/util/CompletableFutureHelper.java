package com.webcrawler.util;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class CompletableFutureHelper {
    /**
     * Execute all futures parallel.
     * @param elements
     * @return List of result.
     * @param <T>
     */
    public static <T> List<T> executeAllFutures(final List<CompletableFuture<T>> elements) {
        try {
            return CompletableFuture.allOf(elements.toArray(new CompletableFuture<?>[0]))
                    .thenApply(v -> elements.stream().map(CompletableFuture::join)
                            .collect(Collectors.toList())).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

package com.iddera.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class CompletableFutureUtil {
    public static <T> CompletableFuture<List<T>> collectAll(Collection<CompletableFuture<T>> futures) {
        return allOf(toJavaCFArray(futures.stream()))
                .thenApply((__) -> futures.stream()
                        .map(CompletableFuture::join)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
    }

    private static CompletableFuture<?>[] toJavaCFArray(Stream<? extends CompletableFuture<?>> cfs) {
        return (CompletableFuture[]) cfs.toArray(CompletableFuture[]::new);
    }

    public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs) {
        return allOf(Stream.of(cfs));
    }

    public static CompletableFuture<Void> allOf(Collection<? extends CompletableFuture<?>> cfs) {
        return allOf(cfs.stream());
    }

    public static CompletableFuture<Void> allOf(Stream<? extends CompletableFuture<?>> cfs) {
        return CompletableFuture.allOf(toJavaCFArray(cfs));
    }
}

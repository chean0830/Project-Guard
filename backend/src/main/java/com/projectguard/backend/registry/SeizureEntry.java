package com.projectguard.backend.registry;

public record SeizureEntry(
        int rank,
        SeizureType type,
        String receivedDate,
        boolean cancelled
) {
}

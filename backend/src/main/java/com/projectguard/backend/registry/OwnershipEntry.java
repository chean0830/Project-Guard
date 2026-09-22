package com.projectguard.backend.registry;

public record OwnershipEntry(
        int rank,
        OwnershipType type,
        String ownerName,
        String receivedDate,
        boolean cancelled
) {
}

package com.projectguard.backend.registry;

public record MortgageEntry(
        int rank,
        long maxClaimAmount,
        String debtorName,
        String mortgageeName,
        String receivedDate,
        boolean cancelled
) {
}

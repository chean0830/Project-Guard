package com.projectguard.backend.registry;

import java.util.List;

public record RegistryAnalysis(
        String address,
        String uniqueNumber,
        List<OwnershipEntry> ownershipHistory,
        List<MortgageEntry> mortgages,
        List<SeizureEntry> seizures,
        long totalActiveMortgageAmount
) {
}

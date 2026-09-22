package com.projectguard.backend.market;

public record AptTradeRecord(
        String complexName,
        long dealAmount,
        double exclusiveAreaSqm,
        int dealYear,
        int dealMonth,
        int dealDay,
        int floor,
        String dongName
) {
}

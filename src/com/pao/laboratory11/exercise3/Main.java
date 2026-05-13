package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        List<Transaction> data = List.of(
                new Transaction(1, new BigDecimal("1200.50"), LocalDate.of(2026, 5, 1), "RO", "WEB"),
                new Transaction(2, new BigDecimal("500.00"), LocalDate.of(2026, 5, 2), "RU", "ATM"),
                new Transaction(3, new BigDecimal("6000.00"), LocalDate.of(2026, 5, 3), "NG", "APP"),
                new Transaction(4, new BigDecimal("6000.00"), LocalDate.of(2026, 5, 4), "RO", "CRYPTO"),
                new Transaction(5, new BigDecimal("100.00"), LocalDate.of(2026, 5, 5), "NL", "WEB"),
                new Transaction(6, new BigDecimal("850.00"), LocalDate.of(2026, 5, 6), "RO", "WEB")
        );

        Snapshot snap = data.stream().collect(CustomCollectors.toSnapshot(3));

        System.out.println("=== SNAPSHOT ANALITIC GENERAT CU SUCCES ===\n");

        System.out.println("--- 1. Volum Total Procesat ---");
        System.out.println("Suma totală: " + snap.totalAmount() + " EUR\n");

        System.out.println("--- 2. Top 3 Tranzacții (desc după sumă, asc după ID) ---");
        snap.topTransactions().forEach(System.out::println);

        System.out.println("\n--- 3. Număr de Tranzacții pe Țări ---");
        snap.countByCountry().entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()
                        .thenComparing(Map.Entry.comparingByKey()))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));

        System.out.println("\n--- 4. Număr de Tranzacții pe Canale ---");
        snap.countByChannel().forEach((channel, count) ->
                System.out.println(channel + ": " + count));
    }
}
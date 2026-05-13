package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCollectors {

    public static Collector<Transaction, ?, Snapshot> toSnapshot(int topN) {

        class Agg {
            Map<String, Long> byCountry = new HashMap<>();
            Map<String, Long> byChannel = new HashMap<>();
            BigDecimal total = BigDecimal.ZERO;
            List<Transaction> transactions = new ArrayList<>();
        }

        return Collector.of(
                Agg::new,
                (agg, tx) -> {
                    agg.byCountry.merge(tx.country(), 1L, Long::sum);
                    agg.byChannel.merge(tx.channel(), 1L, Long::sum);
                    agg.total = agg.total.add(tx.amount());
                    agg.transactions.add(tx);
                },
                (agg1, agg2) -> {
                    agg2.byCountry.forEach((k, v) -> agg1.byCountry.merge(k, v, Long::sum));
                    agg2.byChannel.forEach((k, v) -> agg1.byChannel.merge(k, v, Long::sum));
                    agg1.total = agg1.total.add(agg2.total);
                    agg1.transactions.addAll(agg2.transactions);
                    return agg1;
                },
                agg -> {
                    List<Transaction> top = agg.transactions.stream()
                            .sorted(Comparator.comparing(Transaction::amount).reversed()
                                    .thenComparing(Transaction::id))
                            .limit(topN)
                            .collect(Collectors.toList());
                    return new Snapshot(agg.byCountry, agg.byChannel, agg.total, top);
                }
        );
    }
}
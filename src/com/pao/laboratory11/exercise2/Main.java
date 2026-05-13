package com.pao.laboratory11.exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        try {
            run();
        } catch (IOException e) {
        }
    }

    private static void run() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String first = nextNonEmpty(br);
        if (first == null) {
            return;
        }

        int n = Integer.parseInt(first);
        List<Tx> txs = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String line = nextNonEmpty(br);
            if (line == null) {
                return;
            }

            String[] p = line.split("\\s+");
            txs.add(new Tx(
                    Integer.parseInt(p[0]),
                    Double.parseDouble(p[1]),
                    p[2],
                    p[3],
                    p[4],
                    p[5]));
        }

        int q = Integer.parseInt(nextNonEmpty(br));
        for (int i = 0; i < q; i++) {
            String line = nextNonEmpty(br);
            if (line == null) {
                return;
            }

            String[] p = line.split("\\s+");
            String op = p[0];

            switch (op) {
                case "REPORT_MONTH": {
                    String month = p[1];
                    DoubleSummaryStatistics stats = txs.stream()
                            .filter(tx -> tx.getDate().startsWith(month))
                            .collect(Collectors.summarizingDouble(Tx::getAmount));

                    System.out.printf("MONTH %s total=%.2f count=%d%n",
                            month, stats.getSum(), stats.getCount());
                    break;
                }

                case "REPORT_ACCOUNT": {
                    String account = p[1];
                    DoubleSummaryStatistics stats = txs.stream()
                            .filter(tx -> tx.getAccount().equals(account))
                            .collect(Collectors.summarizingDouble(Tx::getAmount));

                    System.out.printf("ACCOUNT %s total=%.2f count=%d%n",
                            account, stats.getSum(), stats.getCount());
                    break;
                }

                case "TOP_CHANNELS": {
                    int k = Integer.parseInt(p[1]);

                    if (txs.isEmpty()) {
                        System.out.println("NONE");
                        break;
                    }

                    Map<String, Long> counts = txs.stream()
                            .collect(Collectors.groupingBy(Tx::getChannel, Collectors.counting()));

                    counts.entrySet().stream()
                            .sorted(Map.Entry.<String, Long>comparingByValue(Comparator.reverseOrder())
                                    .thenComparing(Map.Entry.comparingByKey()))
                            .limit(k)
                            .forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));
                    break;
                }

                default:
                    break;
            }
        }
    }

    private static String nextNonEmpty(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                return line.trim();
            }
        }
        return null;
    }
}
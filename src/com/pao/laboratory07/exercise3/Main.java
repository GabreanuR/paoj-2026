package com.pao.laboratory07.exercise3;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        if (!sc.hasNextLine()) return;
        int n = Integer.parseInt(sc.nextLine().trim());
        List<Comanda> comenzi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String line = sc.nextLine().trim();
            String[] tokens = line.split(" ");

            try {
                switch (tokens[0]) {
                    case "STANDARD" -> comenzi.add(new ComandaStandard(tokens[1], Double.parseDouble(tokens[2]), tokens[3]));
                    case "DISCOUNTED" -> comenzi.add(new ComandaRedusa(tokens[1], Double.parseDouble(tokens[2]), Integer.parseInt(tokens[3]), tokens[4]));
                    case "GIFT" -> comenzi.add(new ComandaGratuita(tokens[1], tokens[2]));
                    default -> throw new IllegalArgumentException("Tip comandă necunoscut: " + tokens[0]);
                }
            } catch (Exception e) {
                System.out.println("Eroare la parsarea comenzii: " + e.getMessage());
            }
        }

        comenzi.forEach(c -> System.out.println(c.descriere()));

        while (sc.hasNextLine()) {
            String commandLine = sc.nextLine().trim();
            if (commandLine.isEmpty()) continue;

            String[] cmdTokens = commandLine.split(" ");
            String command = cmdTokens[0];

            switch (command) {
                case "STATS" -> {
                    System.out.println("\n--- STATS ---");
                    Map<Class<?>, Double> stats = comenzi.stream()
                            .collect(Collectors.groupingBy(
                                    Object::getClass,
                                    Collectors.averagingDouble(Comanda::pretFinal)
                            ));

                    if (stats.containsKey(ComandaStandard.class))
                        System.out.printf( "STANDARD: medie = %.2f lei\n", stats.get(ComandaStandard.class));
                    if (stats.containsKey(ComandaRedusa.class))
                        System.out.printf("DISCOUNTED: medie = %.2f lei\n", stats.get(ComandaRedusa.class));
                    if (stats.containsKey(ComandaGratuita.class))
                        System.out.printf("GIFT: medie = %.2f lei\n", stats.get(ComandaGratuita.class));
                }

                case "FILTER" -> {
                    double threshold = Double.parseDouble(cmdTokens[1]);
                    System.out.printf("\n--- FILTER (>= %.2f) ---\n", threshold);
                    comenzi.stream()
                            .filter(c -> c.pretFinal() >= threshold)
                            .forEach(c -> System.out.println(c.descriere()));
                }

                case "SORT" -> {
                    System.out.println("\n--- SORT (by client, then by pret) ---");
                    comenzi.stream()
                            .sorted(Comparator.comparing(Comanda::getClient)
                                    .thenComparingDouble(Comanda::pretFinal))
                            .forEach(c -> System.out.println(c.descriere()));
                }

                case "SPECIAL" -> {
                    System.out.println("\n--- SPECIAL (discount > 15%) ---");
                    comenzi.stream()
                            .filter(c -> c instanceof ComandaRedusa cr && cr.getDiscountProcent() > 15)
                            .forEach(c -> System.out.println(c.descriere()));
                }

                case "QUIT" -> {
                    return;
                }

                default -> System.out.println("Comandă necunoscută.");
            }
        }
    }
}
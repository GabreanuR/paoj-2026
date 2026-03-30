package com.pao.laboratory06.exercise2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;

        int n = scanner.nextInt();
        scanner.nextLine();

        List<Colaborator> colaboratori = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String linie = scanner.nextLine().trim();
            if (linie.isEmpty()) continue;

            Scanner linieScanner = new Scanner(linie);
            String tip = linieScanner.next();

            Colaborator c = switch (tip) {
                case "CIM" -> new CIMColaborator();
                case "PFA" -> new PFAColaborator();
                case "SRL" -> new SRLColaborator();
                default -> null;
            };

            if (c != null) {
                c.citeste(linieScanner);
                colaboratori.add(c);
            }
        }

        for (Colaborator c : colaboratori) {
            c.afiseaza();
        }
        System.out.println();

        List<Colaborator> colaboratoriSortati = new ArrayList<>(colaboratori);
        Collections.sort(colaboratoriSortati);

        if (!colaboratoriSortati.isEmpty()) {
            System.out.print("Colaborator cu venit net maxim: ");
            colaboratoriSortati.getFirst().afiseaza();
            System.out.println();
        }

        System.out.println("Colaboratori persoane juridice:");
        for (Colaborator c : colaboratori) {
            if (c instanceof PersoanaJuridica) {
                c.afiseaza();
            }
        }
        System.out.println();

        System.out.println("Sume și număr colaboratori pe tip:");
        Map<TipColaborator, Double> sume = new LinkedHashMap<>();
        Map<TipColaborator, Integer> numaratori = new LinkedHashMap<>();

        for (TipColaborator tip : TipColaborator.values()) {
            sume.put(tip, 0.0);
            numaratori.put(tip, 0);
        }

        for (Colaborator c : colaboratori) {
            TipColaborator tip = TipColaborator.valueOf(c.tipContract());
            sume.put(tip, sume.get(tip) + c.calculeazaVenitNetAnual());
            numaratori.put(tip, numaratori.get(tip) + 1);
        }

        for (TipColaborator tip : TipColaborator.values()) {
            if (numaratori.get(tip) > 0) {
                System.out.printf( "%s: suma = %.2f lei, număr = %d\n", tip.name(), sume.get(tip), numaratori.get(tip));
            }
        }

        scanner.close();
    }
}
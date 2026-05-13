package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) {
        File outDir = new File("output");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;

        int n = Integer.parseInt(sc.nextLine().trim());
        List<Tranzactie> listaOriginala = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] tokens = sc.nextLine().trim().split("\\s+");
            int id = Integer.parseInt(tokens[0]);
            double suma = Double.parseDouble(tokens[1]);
            String data = tokens[2];
            String contSursa = tokens[3];
            String contDestinatie = tokens[4];
            TipTranzactie tip = TipTranzactie.valueOf(tokens[5].toUpperCase());

            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDestinatie, tip);
            t.setNote("procesat");
            listaOriginala.add(t);
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(listaOriginala);
        } catch (IOException e) {
            System.out.println("Eroare la serializare: " + e.getMessage());
            return;
        }

        List<Tranzactie> listaDeserializata;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(OUTPUT_FILE))) {
            listaDeserializata = (List<Tranzactie>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Eroare la deserializare: " + e.getMessage());
            return;
        }

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] tokens = line.split("\\s+");
            String comanda = tokens[0].toUpperCase();

            switch (comanda) {
                case "LIST":
                    for (Tranzactie t : listaDeserializata) {
                        System.out.println(t);
                    }
                    break;

                case "FILTER":
                    if (tokens.length < 2) break;
                    String prefix = tokens[1];
                    boolean gasitFilter = false;
                    for (Tranzactie t : listaDeserializata) {
                        if (t.getData().startsWith(prefix)) {
                            System.out.println(t);
                            gasitFilter = true;
                        }
                    }
                    if (!gasitFilter) {
                        System.out.println("Niciun rezultat.");
                    }
                    break;

                case "NOTE":
                    if (tokens.length < 2) break;
                    int idCautat = Integer.parseInt(tokens[1]);
                    boolean gasitNote = false;
                    for (Tranzactie t : listaDeserializata) {
                        if (t.getId() == idCautat) {
                            System.out.println("NOTE[" + idCautat + "]: " + t.getNote());
                            gasitNote = true;
                            break;
                        }
                    }
                    if (!gasitNote) {
                        System.out.println("NOTE[" + idCautat + "]: not found");
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
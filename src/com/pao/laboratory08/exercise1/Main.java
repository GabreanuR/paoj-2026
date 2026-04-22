package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    // Calea către fișierul cu date — relativă la rădăcina proiectului
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> studenti = new ArrayList<>();

        // 1. Citește studenții din FILE_PATH cu BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Adresa adresa = new Adresa(parts[2].trim(), parts[3].trim());
                    Student student = new Student(parts[0].trim(), Integer.parseInt(parts[1].trim()), adresa);
                    studenti.add(student);
                }
            }
        } catch (IOException e) {
            System.out.println("Eroare la citirea fișierului: " + e.getMessage());
            return;
        }

        // 2. Citește comanda din stdin
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;

        String commandLine = sc.nextLine().trim();

        String[] tokens = commandLine.split(" ", 2);
        String actiune = tokens[0];

        // 3. Execută comanda
        if (actiune.equals("PRINT")) {
            for (Student s : studenti) {
                System.out.println(s);
            }
        } else if (actiune.equals("SHALLOW") || actiune.equals("DEEP")) {
            if (tokens.length < 2) {
                System.out.println("Numele studentului lipsește din comandă.");
                return;
            }

            String numeCautat = tokens[1];
            Student original = null;

            for (Student s : studenti) {
                if (s.getNume().equals(numeCautat)) {
                    original = s;
                    break;
                }
            }

            if (original != null) {
                try {
                    Student clona = actiune.equals("SHALLOW") ? original.shallowClone() : original.deepClone();

                    clona.getAdresa().setOras("MODIFICAT");

                    System.out.println("Original: " + original);
                    System.out.println("Clona: " + clona);
                } catch (CloneNotSupportedException e) {
                    System.out.println("Clonarea a eșuat: " + e.getMessage());
                }
            } else {
                System.out.println("Studentul " + numeCautat + " nu a fost găsit.");
            }
        }
    }
}



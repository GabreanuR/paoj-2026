package com.pao.laboratory08.exercise2;

import com.pao.laboratory08.exercise1.Adresa;
import com.pao.laboratory08.exercise1.Student;

import java.io.*;
import java.util.*;

public class Main {
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

        // 2. Citește pragul de vârstă din stdin cu Scanner
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int prag = sc.nextInt();

        // 3. Filtrează studenții cu varsta >= prag
        List<Student> rezultate = new ArrayList<>();
        for (Student s : studenti) {
            if (s.getVarsta() >= prag) {
                rezultate.add(s);
            }
        }

        // 4. Afișează sumarul la consolă (Facem afișarea întâi pentru a urmări formatul cerut)
        System.out.println("Filtru: varsta >= " + prag);
        System.out.println("Rezultate: " + rezultate.size() + " studenti\n");

        for (Student s : rezultate) {
            System.out.println(s);
        }

        System.out.println("\nScris in: rezultate.txt");

        // 5. Scrie filtrații în "rezultate.txt" cu BufferedWriter
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rezultate.txt"))) {
            for (Student s : rezultate) {
                bw.write(s.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Eroare la scrierea fișierului: " + e.getMessage());
        }
    }
}


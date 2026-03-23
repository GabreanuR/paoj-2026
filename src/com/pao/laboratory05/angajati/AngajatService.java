package com.pao.laboratory05.angajati;

import java.util.Arrays;

public class AngajatService {
    private Angajat[] angajati;

    private AngajatService() {
        this.angajati = new Angajat[0];
    }

    private static class Holder {
        private static final AngajatService INSTANCE = new AngajatService();
    }

    public static AngajatService getInstance() {
        return Holder.INSTANCE;
    }

    public void addAngajat(Angajat a) {
        Angajat[] copy = new Angajat[angajati.length + 1];
        System.arraycopy(angajati, 0, copy, 0, angajati.length);
        copy[angajati.length] = a;
        this.angajati = copy;

        System.out.println("Angajat adăugat: " + a.getNume());
    }

    public void printAll() {
        if (angajati.length == 0) {
            System.out.println("Nu există angajați în sistem.");
            return;
        }
        for (Angajat a : angajati) {
            System.out.println(a);
        }
    }

    public void listBySalary() {
        if (angajati.length == 0) {
            System.out.println("Nu există angajați în sistem.");
            return;
        }
        Angajat[] copy = angajati.clone();
        Arrays.sort(copy);

        for (int i = 0; i < copy.length; i++) {
            System.out.println((i + 1) + ". " + copy[i]);
        }
    }

    public void findByDepartament(String numeDept) {
        boolean found = false;
        for (Angajat a : angajati) {
            if (a.getDepartament().nume().equalsIgnoreCase(numeDept)) {
                System.out.println(a);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Niciun angajat în departamentul: " + numeDept);
        }
    }
}
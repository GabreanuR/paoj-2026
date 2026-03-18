package com.pao.laboratory02.exercise4.service;

import com.pao.laboratory02.exercise4.model.Animal;

import java.util.ArrayList;
import java.util.List;

/**
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │  TODO — Completează metodele din ZooService                            │
 * └─────────────────────────────────────────────────────────────────────────┘
 *
 * Serviciu Singleton care gestionează animalele din grădina zoologică.
 * Folosește ArrayList<Animal> intern.
 *
 * Pattern: Bill Pugh Singleton (la fel ca CarService din Lab 01).
 * Partea de Singleton este DATĂ — tu completezi doar operațiile (4 metode).
 *
 * Ce trebuie să faci:
 *
 *   1. addAnimal(Animal a)
 *      - Adaugă animalul în lista internă.
 *      - Afișează: "Adăugat: " + a
 *
 *   2. listAll()
 *      - Dacă lista e goală → afișează "Grădina zoologică este goală."
 *      - Altfel, pentru fiecare animal afișează describe() (din interfața Describable).
 *      - Format: "  1. Rex (varsta: 5 ani) face: Ham!"
 *
 *   3. listByType(String type)
 *      - Parcurge lista și afișează doar animalele al căror getClass().getSimpleName()
 *        este egal cu type (ex: "Dog", "Cat", "Parrot").
 *      - Dacă nu găsește niciunul, afișează: "Nu există animale de tipul: " + type
 *      - Hint: folosește animal.getClass().getSimpleName().equals(type)
 *
 *   4. findOldest()
 *      - Dacă lista e goală → afișează "Grădina zoologică este goală."
 *      - Altfel, parcurge lista și găsește animalul cu vârsta maximă.
 *      - Afișează: "Cel mai bătrân animal: " + animal.describe()
 *      - Hint: ține o variabilă Animal oldest = animals.get(0), apoi compară cu fiecare.
 */
public class ZooService {

    private List<Animal> animals;

    // === Singleton (DAT — nu modifica) ===
    private ZooService() {
        this.animals = new ArrayList<>();
    }

    private static class Holder {
        private static final ZooService INSTANCE = new ZooService();
    }

    public static ZooService getInstance() {
        return Holder.INSTANCE;
    }
    // === Sfârșit Singleton ===

    public void addAnimal(Animal a) {
        animals.add(a);
        System.out.println("Adăugat: " + a);
    }

    public void listAll() {
        if (animals.isEmpty()) {
            System.out.println("Grădina zoologică este goală.");
            return;
        }

        for (int i = 0; i < animals.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + animals.get(i).describe());
        }
    }

    public void listByType(String type) {
        boolean found = false;

        for (Animal animal : animals) {
            if (animal.getClass().getSimpleName().equals(type)) {
                System.out.println("  - " + animal.describe());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Nu există animale de tipul: " + type);
        }
    }

    public void findOldest() {
        if (animals.isEmpty()) {
            System.out.println("Grădina zoologică este goală.");
            return;
        }

        Animal oldest = animals.get(0);

        for (int i = 1; i < animals.size(); i++) {
            if (animals.get(i).getAge() > oldest.getAge()) {
                oldest = animals.get(i);
            }
        }

        System.out.println("Cel mai bătrân animal: " + oldest.describe());
    }
}


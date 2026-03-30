package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class PFAColaborator extends Colaborator implements PersoanaFizica {
    private double cheltuieliLunare;

    @Override
    public void citeste(Scanner in) {
        super.citeste(in);
        if (in.hasNextDouble()) {
            this.cheltuieliLunare = in.nextDouble();
        }
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double venitNet = (venitBrutLunar - cheltuieliLunare) * 12;
        double impozit = 0.10 * venitNet;

        double salMinL = 4050;

        double cass = 0;
        if (venitNet < 6 * salMinL) {
            cass = 0.10 * (6 * salMinL);
        } else if (venitNet <= 72 * salMinL) {
            cass = 0.10 * venitNet;
        } else {
            cass = 0.10 * (72 * salMinL);
        }

        double cas = 0;
        if (venitNet < 12 * salMinL) {
            cas = 0;
        } else if (venitNet <= 24 * salMinL) {
            cas = 0.25 * (12 * salMinL);
        } else {
            cas = 0.25 * (24 * salMinL);
        }

        return venitNet - impozit - cass - cas;
    }

    @Override
    public String tipContract() {
        return TipColaborator.PFA.name();
    }
}
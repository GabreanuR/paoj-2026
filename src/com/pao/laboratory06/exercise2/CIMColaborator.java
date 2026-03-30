package com.pao.laboratory06.exercise2;

import java.util.Scanner;

public class CIMColaborator extends Colaborator implements PersoanaFizica {
    private boolean bonus;

    @Override
    public void citeste(Scanner in) {
        super.citeste(in);
        if (in.hasNext()) {
            String valoareBonus = in.next();
            if (valoareBonus.equalsIgnoreCase("DA")) {
                this.bonus = true;
            }
        }
    }

    @Override
    public double calculeazaVenitNetAnual() {
        double net = venitBrutLunar * 12 * 0.55;
        if (bonus) {
            net *= 1.10;
        }
        return net;
    }

    @Override
    public String tipContract() {
        return TipColaborator.CIM.name();
    }

    @Override
    public boolean areBonus() {
        return bonus;
    }
}
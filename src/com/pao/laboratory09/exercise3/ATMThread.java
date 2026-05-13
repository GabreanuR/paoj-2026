package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.TipTranzactie;
import com.pao.laboratory09.exercise1.Tranzactie;

public class ATMThread extends Thread {
    private final CoadaTranzactii coada;
    private final int idAtm;
    private static int counterGlobal = 0;

    public ATMThread(CoadaTranzactii coada, int idAtm) {
        this.coada = coada;
        this.idAtm = idAtm;
    }

    private synchronized int generareId() {
        return ++counterGlobal;
    }

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            int id = generareId();
            double suma = 100 + (Math.random() * 900);

            Tranzactie t = new Tranzactie(id, suma, "2026-05-13", "ATM-" + idAtm, "BANK", TipTranzactie.CREDIT);

            System.out.printf("[ATM-%d] trimite: Tranzactie #%d %.2f RON\n", idAtm, t.getId(), t.getSuma());
            coada.adauga(t, "ATM-" + idAtm);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
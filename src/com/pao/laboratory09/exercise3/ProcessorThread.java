package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.Tranzactie;

public class ProcessorThread implements Runnable {
    private final CoadaTranzactii coada;
    public volatile boolean activ = true;

    public ProcessorThread(CoadaTranzactii coada) {
        this.coada = coada;
    }

    @Override
    public void run() {
        while (activ || coada.areElemente()) {
            Tranzactie t = coada.extrage();
            if (t != null) {
                System.out.printf("[Processor] Factura #%d - %.2f RON | %s\n",
                        t.getId(), t.getSuma(), t.getData());
                try {
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
package com.pao.laboratory09.exercise3;

public class Main {
    public static void main(String[] args) {
        CoadaTranzactii coada = new CoadaTranzactii();

        ATMThread atm1 = new ATMThread(coada, 1);
        ATMThread atm2 = new ATMThread(coada, 2);
        ATMThread atm3 = new ATMThread(coada, 3);

        ProcessorThread processor = new ProcessorThread(coada);
        Thread pThread = new Thread(processor);

        System.out.println("--- Start Procesare Asincrona ---");

        atm1.start();
        atm2.start();
        atm3.start();
        pThread.start();

        try {
            atm1.join();
            atm2.join();
            atm3.join();

            processor.activ = false;
            coada.opreste();

            pThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Toate tranzactiile procesate. Total: 12");
    }
}
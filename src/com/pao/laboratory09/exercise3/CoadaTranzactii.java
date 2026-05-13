package com.pao.laboratory09.exercise3;

import com.pao.laboratory09.exercise1.Tranzactie;
import java.util.LinkedList;
import java.util.Queue;

public class CoadaTranzactii {
    private final Queue<Tranzactie> queue = new LinkedList<>();
    private final int capacitate = 5;
    private boolean shutdown = false;

    public synchronized void adauga(Tranzactie t, String atmName) {
        while (queue.size() == capacitate) {
            System.out.println("[" + atmName + "] astept loc...");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        queue.add(t);
        notifyAll();
    }

    public synchronized Tranzactie extrage() {
        while (queue.isEmpty() && !shutdown) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (queue.isEmpty() && shutdown) {
            return null;
        }

        Tranzactie t = queue.poll();
        notifyAll();
        return t;
    }

    public synchronized void opreste() {
        shutdown = true;
        notifyAll();
    }

    public synchronized boolean areElemente() {
        return !queue.isEmpty();
    }
}
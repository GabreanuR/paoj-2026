package com.pao.proiect.fooddelivery.model;

import java.util.UUID;

public class MenuItem {
    private final String id;
    private String nume;
    private double pret;

    public MenuItem(String nume, double pret) {
        this.id = UUID.randomUUID().toString();
        this.nume = nume;
        this.pret = pret;
    }

    public String getId() { return id; }
    public String getNume() { return nume; }
    public double getPret() { return pret; }

    public void setNume(String nume) { this.nume = nume; }
    public void setPret(double pret) { this.pret = pret; }

    @Override
    public String toString() {
        return nume + " (" + pret + " RON)";
    }
}
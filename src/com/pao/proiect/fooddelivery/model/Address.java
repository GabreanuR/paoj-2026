package com.pao.proiect.fooddelivery.model;

public class Address {
    private final String strada;
    private final String oras;
    private final String codPostal;

    public Address(String strada, String oras, String codPostal) {
        this.strada = strada;
        this.oras = oras;
        this.codPostal = codPostal;
    }

    public String getStrada() { return strada; }
    public String getOras() { return oras; }
    public String getCodPostal() { return codPostal; }

    @Override
    public String toString() {
        return strada + ", " + oras + " (" + codPostal + ")";
    }
}
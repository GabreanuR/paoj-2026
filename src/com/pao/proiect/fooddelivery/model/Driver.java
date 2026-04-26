package com.pao.proiect.fooddelivery.model;

public class Driver extends User {
    private String numarInmatriculare;
    private boolean esteDisponibil;

    public Driver(String nume, String telefon, String email, String numarInmatriculare) {
        super(nume, telefon, email);
        this.numarInmatriculare = numarInmatriculare;
        this.esteDisponibil = true;
    }

    public String getNumarInmatriculare() { return numarInmatriculare; }
    public boolean isEsteDisponibil() { return esteDisponibil; }

    public void setNumarInmatriculare(String numarInmatriculare) { this.numarInmatriculare = numarInmatriculare; }
    public void setEsteDisponibil(boolean esteDisponibil) { this.esteDisponibil = esteDisponibil; }

    @Override
    public String toString() {
        return "Sofer [" + super.toString() + ", Mașină: " + numarInmatriculare + ", Disponibil: " + esteDisponibil + "]";
    }
}
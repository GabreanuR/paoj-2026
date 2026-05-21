package com.pao.proiect.fooddelivery.model;

import java.util.UUID;

public abstract class User {
    protected String id;
    protected String nume;
    protected String telefon;
    protected String email;

    public User(String nume, String telefon, String email) {
        this.id = UUID.randomUUID().toString();
        this.nume = nume;
        this.telefon = telefon;
        this.email = email;
    }

    public String getId() { return id; }
    public String getNume() { return nume; }
    public String getTelefon() { return telefon; }
    public String getEmail() { return email; }

    public void setId(String id) { this.id = id; }
    public void setTelefon(String telefon) { this.telefon = telefon; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Nume: " + nume + ", Telefon: " + telefon;
    }
}
package com.pao.proiect.fooddelivery.model;

public class Client extends User {
    private Address adresaLivrare;

    public Client(String nume, String telefon, String email, Address adresaLivrare) {
        super(nume, telefon, email);
        this.adresaLivrare = adresaLivrare;
    }

    public Address getAdresaLivrare() { return adresaLivrare; }

    public void setAdresaLivrare(Address adresaLivrare) { this.adresaLivrare = adresaLivrare; }

    @Override
    public String toString() {
        return "Client [" + super.toString() + ", Adresa: " + adresaLivrare + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return this.getEmail().equals(client.getEmail());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(this.getEmail());
    }
}
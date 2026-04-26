package com.pao.proiect.fooddelivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Objects;

public class Restaurant implements Comparable<Restaurant> {
    private final String id;
    private final String nume;
    private final Address adresa;
    private double rating;
    private int numarReviews;
    private final List<MenuItem> meniu;

    public Restaurant(String nume, Address adresa) {
        this.id = UUID.randomUUID().toString();
        this.nume = nume;
        this.adresa = adresa;
        this.rating = 0.0;
        this.numarReviews = 0;
        this.meniu = new ArrayList<>();
    }

    public Restaurant(String id, String nume, Address adresa, double rating, int numarReviews) {
        this.id = id;
        this.nume = nume;
        this.adresa = adresa;
        this.rating = rating;
        this.numarReviews = numarReviews;
        this.meniu = new ArrayList<>();
    }

    public String getId() { return id; }
    public String getNume() { return nume; }
    public Address getAdresa() { return adresa; }
    public double getRating() { return rating; }
    public int getNumarReviews() { return numarReviews; }
    public List<MenuItem> getMeniu() { return meniu; }

    public void addMenuItem(MenuItem item) {
        this.meniu.add(item);
    }

    public void updateRating(int notaNoua) {
        double sumaNoteCurente = this.rating * this.numarReviews;
        this.numarReviews++;
        this.rating = (sumaNoteCurente + notaNoua) / this.numarReviews;
        this.rating = Math.round(this.rating * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Restaurant: " + nume + " | Rating: " + rating + " stele (" + numarReviews + " recenzii)";
    }

    @Override
    public int compareTo(Restaurant altRestaurant) {
        return Double.compare(altRestaurant.getRating(), this.rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
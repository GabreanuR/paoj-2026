package com.pao.proiect.fooddelivery.model;

import java.util.UUID;

public class Review {
    private final String id;
    private final Client client;
    private final Restaurant restaurant;
    private int nota;
    private String comentariu;

    public Review(Client client, Restaurant restaurant, int nota, String comentariu) {
        this.id = UUID.randomUUID().toString();
        this.client = client;
        this.restaurant = restaurant;
        this.nota = nota;
        this.comentariu = comentariu;
    }

    public String getId() { return id; }
    public Client getClient() { return client; }
    public Restaurant getRestaurant() { return restaurant; }
    public int getNota() { return nota; }
    public String getComentariu() { return comentariu; }

    public void setNota(int nota) { this.nota = nota; }
    public void setComentariu(String comentariu) { this.comentariu = comentariu; }


    @Override
    public String toString() {
        return "Review de la: " + client.getNume() + " pt " + restaurant.getNume() +
                " | Nota: " + nota + "/5 | Mesaj: " + comentariu;
    }
}
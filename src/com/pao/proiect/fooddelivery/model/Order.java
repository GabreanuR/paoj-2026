package com.pao.proiect.fooddelivery.model;

import java.util.List;
import java.util.UUID;

public class Order {
    private final String id;
    private final Client client;
    private final Restaurant restaurant;
    private final List<MenuItem> produse;
    private Driver sofer;
    private final double pretTotal;
    private String status;

    public Order(Client client, Restaurant restaurant, List<MenuItem> produse) {
        this.id = UUID.randomUUID().toString();
        this.client = client;
        this.restaurant = restaurant;
        this.produse = produse;
        this.status = "PLASATA";
        this.pretTotal = calculeazaPretTotal(produse);
    }

    private double calculeazaPretTotal(List<MenuItem> produse) {
        double total = 0;
        for (MenuItem item : produse) {
            total += item.getPret();
        }
        return total;
    }

    public String getId() { return id; }
    public Client getClient() { return client; }
    public Restaurant getRestaurant() { return restaurant; }
    public List<MenuItem> getProduse() { return produse; }
    public Driver getSofer() { return sofer; }
    public String getStatus() { return status; }
    public double getPretTotal() { return pretTotal; }

    public void setSofer(Driver sofer) { this.sofer = sofer; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        String numeSofer = (sofer != null) ? sofer.getNume() : "Nealocat";
        return "Comanda [" + id.substring(0, 8) + "] | Client: " + client.getNume() +
                " | Restaurant: " + restaurant.getNume() + " | Total: " + pretTotal +
                " RON | Status: " + status + " | Șofer: " + numeSofer;
    }
}
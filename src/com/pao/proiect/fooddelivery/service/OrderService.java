package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.model.*;
import com.pao.proiect.fooddelivery.exception.NoAvailableDriverException;

import java.util.*;

public class OrderService {
    // Implementare Singleton
    private static OrderService instance;

    private final List<Order> comenzi;
    private final List<Review> recenzii;
    private final Set<Restaurant> restaurante;

    private OrderService() {
        this.comenzi = new ArrayList<>();
        this.recenzii = new ArrayList<>();
        this.restaurante = new TreeSet<>();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderService();
        }
        return instance;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurante.add(restaurant);
        System.out.println("Restaurant adăugat: " + restaurant.getNume());
    }

    public Order placeOrder(Client client, Restaurant restaurant, List<MenuItem> produse) {
        Order comanda = new Order(client, restaurant, produse);
        comenzi.add(comanda);
        System.out.println("Comandă plasată cu succes de " + client.getNume());
        return comanda;
    }

    public void assignDriverToOrder(Order comanda) {
        List<Driver> soferi = UserService.getInstance().getSoferi(); // Comunicăm cu celălalt serviciu
        for (Driver sofer : soferi) {
            if (sofer.isEsteDisponibil()) {
                comanda.setSofer(sofer);
                sofer.setEsteDisponibil(false);
                comanda.setStatus("IN_LIVRARE");
                System.out.println("Șoferul " + sofer.getNume() + " a preluat comanda.");
                return;
            }
        }
        // BIFĂM CERINȚA: Excepție custom când nu găsim șofer
        throw new NoAvailableDriverException("Sistemul este aglomerat. Niciun șofer nu este disponibil!");
    }

    public void addReview(Client client, Restaurant restaurant, int nota, String mesaj) {
        Review review = new Review(client, restaurant, nota, mesaj);
        recenzii.add(review);
        restaurant.updateRating(nota);
        System.out.println("Review adăugat! Noul rating pt " + restaurant.getNume() + " este: " + restaurant.getRating());
    }

    public void getTopRestaurants() {
        System.out.println("--- Top Restaurante ---");
        for (Restaurant r : restaurante) {
            System.out.println(r);
        }
    }
}
package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.model.Client;
import com.pao.proiect.fooddelivery.model.Driver;
import com.pao.proiect.fooddelivery.exception.UserNotFoundException;

import java.util.*;

public class UserService {
    private static UserService instance;

    private final Map<String, Client> clienti;
    private final List<Driver> soferi;

    private UserService() {
        this.clienti = new HashMap<>();
        this.soferi = new ArrayList<>();
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void addClient(Client client) {
        clienti.put(client.getEmail(), client);
        System.out.println("Client adăugat: " + client.getNume());
    }

    public Client findClientByEmail(String email) {
        if (!clienti.containsKey(email)) {
            throw new UserNotFoundException("Nu a fost găsit niciun client cu emailul: " + email);
        }
        return clienti.get(email);
    }

    public void addDriver(Driver sofer) {
        soferi.add(sofer);
        System.out.println("Șofer adăugat: " + sofer.getNume());
    }

    public List<Driver> getSoferi() {
        return soferi;
    }

    public void afiseazaTotiClientii() {
        System.out.println("--- Lista clienților ---");
        for (Client client : clienti.values()) {
            System.out.println(client);
        }
    }

    public void deleteClient(String email) {
        if (!clienti.containsKey(email)) {
            throw new UserNotFoundException("Nu putem șterge: Clientul cu emailul " + email + " nu există!");
        }
        Client sters = clienti.remove(email);
        System.out.println("Clientul " + sters.getNume() + " a fost șters din sistem.");
    }
}
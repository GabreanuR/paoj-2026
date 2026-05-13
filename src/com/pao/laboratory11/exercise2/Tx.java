package com.pao.laboratory11.exercise2;

public class Tx {
    private final int id;
    private final double amount;
    private final String date;
    private final String country;
    private final String channel;
    private final String account;

    public Tx(int id, double amount, String date, String country, String channel, String account) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.country = country;
        this.channel = channel;
        this.account = account;
    }

    public int getId() { return id; }
    public double getAmount() { return amount; }
    public String getDate() { return date; }
    public String getCountry() { return country; }
    public String getChannel() { return channel; }
    public String getAccount() { return account; }
}
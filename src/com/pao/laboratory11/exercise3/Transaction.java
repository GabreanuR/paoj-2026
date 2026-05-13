package com.pao.laboratory11.exercise3;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Transaction(int id, BigDecimal amount, LocalDate date, String country, String channel) {

    @Override
    public String toString() {
        return String.format("Tx[ID=%d, Amount=%s, Country=%s, Channel=%s]", id, amount, country, channel);
    }
}
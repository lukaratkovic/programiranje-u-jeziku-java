package hr.java.production.model;

import java.time.LocalDateTime;

public class Order {
    String oznaka;
    String slanje;
    String napomena;
    LocalDateTime datumKreiranja;

    public Order(String oznaka, String slanje, String napomena, LocalDateTime datumKreiranja) {
        this.oznaka = oznaka;
        this.slanje = slanje;
        this.napomena = napomena;
        this.datumKreiranja = datumKreiranja;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    public String getSlanje() {
        return slanje;
    }

    public void setSlanje(String slanje) {
        this.slanje = slanje;
    }

    public String getNapomena() {
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public LocalDateTime getDatumKreiranja() {
        return datumKreiranja;
    }

    public void setDatumKreiranja(LocalDateTime datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }
}

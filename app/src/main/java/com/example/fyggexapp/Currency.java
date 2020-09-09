package com.example.fyggexapp;

public class Currency {

    private String name;
    private double euroPrice;

    public Currency(String name, double euroPrice) {
        this.name = name;
        this.euroPrice = euroPrice;
    }

    public Currency(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getEuroPrice() {
        return euroPrice;
    }

    public void setEuroPrice(double euroPrice) {
        this.euroPrice = euroPrice;
    }
}
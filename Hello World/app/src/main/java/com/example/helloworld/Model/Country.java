package com.example.helloworld.Model;

import com.google.gson.annotations.SerializedName;

public class Country {
    private int id;
    private String name;
    private int population;
    @SerializedName("land_area")
    private double landArea;
    private double density;
    private String capital;
    private String currency;
    private String flag;
    private boolean locked;

    public Country(int id, String name, int population, double landArea, double density, String capital, String currency, String flag, boolean locked) {
        this.id = id;
        this.name = name;
        this.population = population;
        this.landArea = landArea;
        this.density = density;
        this.capital = capital;
        this.currency = currency;
        this.flag = flag;
        this.locked = locked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public double getLandArea() {
        return landArea;
    }

    public void setLandArea(int landArea) {
        this.landArea = landArea;
    }

    public double getDensity() {
        return density;
    }

    public void setDensity(double density) {
        this.density = density;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}

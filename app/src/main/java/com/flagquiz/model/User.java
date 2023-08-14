package com.flagquiz.model;

public class User {
    private int id;
    private int hardcoreGlobal;
    private int hardcoreEurope;
    private int hardcoreAmerica;
    private int hardcoreAsia;
    private int hardcoreOceania;
    private int hardcoreAfrica;
    private int points;

    public User(int id, int hardcoreGlobal, int hardcoreEurope, int hardcoreAmerica,
                int hardcoreAsia, int hardcoreOceania, int hardcoreAfrica, int points) {
        this.id = id;
        this.hardcoreGlobal = hardcoreGlobal;
        this.hardcoreEurope = hardcoreEurope;
        this.hardcoreAmerica = hardcoreAmerica;
        this.hardcoreAsia = hardcoreAsia;
        this.hardcoreOceania = hardcoreOceania;
        this.hardcoreAfrica = hardcoreAfrica;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHardcoreGlobal() {
        return hardcoreGlobal;
    }

    public void setHardcoreGlobal(int hardcoreGlobal) {
        this.hardcoreGlobal = hardcoreGlobal;
    }

    public int getHardcoreEurope() {
        return hardcoreEurope;
    }

    public void setHardcoreEurope(int hardcoreEurope) {
        this.hardcoreEurope = hardcoreEurope;
    }

    public int getHardcoreAmerica() {
        return hardcoreAmerica;
    }

    public void setHardcoreAmerica(int hardcoreAmerica) {
        this.hardcoreAmerica = hardcoreAmerica;
    }

    public int getHardcoreAsia() {
        return hardcoreAsia;
    }

    public void setHardcoreAsia(int hardcoreAsia) {
        this.hardcoreAsia = hardcoreAsia;
    }

    public int getHardcoreOceania() {
        return hardcoreOceania;
    }

    public void setHardcoreOceania(int hardcoreOceania) {
        this.hardcoreOceania = hardcoreOceania;
    }

    public int getHardcoreAfrica() {
        return hardcoreAfrica;
    }

    public void setHardcoreAfrica(int hardcoreAfrica) {
        this.hardcoreAfrica = hardcoreAfrica;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
package com.flagquiz.model;

public class User {
    private int id;
    private int hardcoreGlobal;
    private int hardcoreEurope;
    private int hardcoreAmerica;
    private int hardcoreAsia;
    private int hardcoreOceania;
    private int hardcoreAfrica;

    private int timeGlobal;
    private int timeEurope;
    private int timeAmerica;
    private int timeAsia;
    private int timeOceania;
    private int timeAfrica;
    private int points;

    public User(int id, int hardcoreGlobal, int hardcoreEurope, int hardcoreAmerica, int hardcoreAsia,
                int hardcoreOceania, int hardcoreAfrica, int timeGlobal, int timeEurope, int timeAmerica,
                int timeAsia, int timeOceania, int timeAfrica, int points) {
        this.id = id;
        this.hardcoreGlobal = hardcoreGlobal;
        this.hardcoreEurope = hardcoreEurope;
        this.hardcoreAmerica = hardcoreAmerica;
        this.hardcoreAsia = hardcoreAsia;
        this.hardcoreOceania = hardcoreOceania;
        this.hardcoreAfrica = hardcoreAfrica;
        this.timeGlobal = timeGlobal;
        this.timeEurope = timeEurope;
        this.timeAmerica = timeAmerica;
        this.timeAsia = timeAsia;
        this.timeOceania = timeOceania;
        this.timeAfrica = timeAfrica;
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

    public int getTimeGlobal() {
        return timeGlobal;
    }

    public void setTimeGlobal(int timeGlobal) {
        this.timeGlobal = timeGlobal;
    }

    public int getTimeEurope() {
        return timeEurope;
    }

    public void setTimeEurope(int timeEurope) {
        this.timeEurope = timeEurope;
    }

    public int getTimeAmerica() {
        return timeAmerica;
    }

    public void setTimeAmerica(int timeAmerica) {
        this.timeAmerica = timeAmerica;
    }

    public int getTimeAsia() {
        return timeAsia;
    }

    public void setTimeAsia(int timeAsia) {
        this.timeAsia = timeAsia;
    }

    public int getTimeOceania() {
        return timeOceania;
    }

    public void setTimeOceania(int timeOceania) {
        this.timeOceania = timeOceania;
    }

    public int getTimeAfrica() {
        return timeAfrica;
    }

    public void setTimeAfrica(int timeAfrica) {
        this.timeAfrica = timeAfrica;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
package com.flagquiz.model;

public class Flag {

    private String image;
    private String name;
    private int difficulty;
    private int poblation;
    private String capital;
    private String region;

    public Flag(String image, String name, int difficulty, int poblation, String capital, String region) {
        this.image = image;
        this.name = name;
        this.difficulty = difficulty;
        this.poblation = poblation;
        this.capital = capital;
        this.region = region;
    }

    public Flag() {

    }

    public Flag(Flag flag) {
        this.image = flag.getImage();
        this.name = flag.getName();
        this.difficulty = flag.getDifficulty();
        this.poblation = flag.getPoblation();
        this.capital = flag.getCapital();
        this.region = flag.getRegion();
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getPoblation() {
        return poblation;
    }

    public String getRegion() {
        return region;
    }
}
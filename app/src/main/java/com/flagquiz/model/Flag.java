package com.flagquiz.model;

public class Flag {

    private String image;
    private String name;
    private int difficulty;
    private int poblation;
    private String region;

    public Flag(String image, String name, int difficulty,int poblation, String region) {
        this.image = image;
        this.name = name;
        this.difficulty = difficulty;
        this.poblation = poblation;
        this.region = region;
    }

    public Flag() {

    }

    public Flag(Flag flag) {
        this.image = flag.getImage();
        this.name = flag.getName();
        this.difficulty = flag.getDifficulty();
        this.poblation = flag.getPoblation();
        this.region = flag.getRegion();
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
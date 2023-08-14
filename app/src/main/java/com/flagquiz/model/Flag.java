package com.flagquiz.model;

public class Flag {

    private String image;
    private String name;
    private int difficulty;
    private String region;

    public Flag(String image, String name, int difficulty, String region) {
        this.image = image;
        this.name = name;
        this.difficulty = difficulty;
        this.region = region;
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

    public String getRegion() {
        return region;
    }

}
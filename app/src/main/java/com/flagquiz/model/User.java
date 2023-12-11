package com.flagquiz.model;

public class User {
    private int id;
/** MODE HARDCORE*/
    private int hardcore_1;
    private int hardcore_2;
    private int hardcore_3;
    private int hardcore_4;
    private int hardcore_5;
    private int hardcore_6;
    private int hardcore_7;
    private int hardcore_8;
    private int hardcore_9;
    private int hardcore_10;
/** MODE TIME */
    private int timeEasy;
    private int timeMedium;
    private int timeHard;
    private int timeExtreme;
    private int timeInsane;
/** MODE FLAG */
    private int flagEasy;
    private int flagMedium;
    private int flagHard;
    private int flagExtreme;
    private int flagInsane;
/** MODE COUNTRY */
    private int countryEasy;
    private int countryMedium;
    private int countryHard;
    private int countryExtreme;
    private int countryInsane;

    private int points;

    public User(int id, int hardcore_1, int hardcore_2, int hardcore_3, int hardcore_4,
                int hardcore_5, int hardcore_6, int hardcore_7, int hardcore_8, int hardcore_9,
                int hardcore_10, int timeEasy, int timeMedium, int timeHard,
                int timeExtreme, int timeInsane, int flagEasy, int flagMedium, int flagHard,
                int flagExtreme, int flagInsane, int countryEasy, int countryMedium, int countryHard,
                int countryExtreme, int countryInsane, int points) {
        this.id = id;
        this.hardcore_1 = hardcore_1;
        this.hardcore_2 = hardcore_2;
        this.hardcore_3 = hardcore_3;
        this.hardcore_4 = hardcore_4;
        this.hardcore_5 = hardcore_5;
        this.hardcore_6 = hardcore_6;
        this.hardcore_7 = hardcore_7;
        this.hardcore_8 = hardcore_8;
        this.hardcore_9 = hardcore_9;
        this.hardcore_10 = hardcore_10;
        this.timeEasy = timeEasy;
        this.timeMedium = timeMedium;
        this.timeHard = timeHard;
        this.timeExtreme = timeExtreme;
        this.timeInsane = timeInsane;
        this.flagEasy = flagEasy;
        this.flagMedium = flagMedium;
        this.flagHard = flagHard;
        this.flagExtreme = flagExtreme;
        this.flagInsane = flagInsane;
        this.countryEasy = countryEasy;
        this.countryMedium = countryMedium;
        this.countryHard = countryHard;
        this.countryExtreme = countryExtreme;
        this.countryInsane = countryInsane;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHardcore_1() {
        return hardcore_1;
    }

    public void setHardcore_1(int hardcore_1) {
        this.hardcore_1 = hardcore_1;
    }

    public int getHardcore_2() {
        return hardcore_2;
    }

    public void setHardcore_2(int hardcore_2) {
        this.hardcore_2 = hardcore_2;
    }

    public int getHardcore_3() {
        return hardcore_3;
    }

    public void setHardcore_3(int hardcore_3) {
        this.hardcore_3 = hardcore_3;
    }

    public int getHardcore_4() {
        return hardcore_4;
    }

    public void setHardcore_4(int hardcore_4) {
        this.hardcore_4 = hardcore_4;
    }

    public int getHardcore_5() {
        return hardcore_5;
    }

    public void setHardcore_5(int hardcore_5) {
        this.hardcore_5 = hardcore_5;
    }

    public int getHardcore_6() {
        return hardcore_6;
    }

    public void setHardcore_6(int hardcore_6) {
        this.hardcore_6 = hardcore_6;
    }

    public int getHardcore_7() {
        return hardcore_7;
    }

    public void setHardcore_7(int hardcore_7) {
        this.hardcore_7 = hardcore_7;
    }

    public int getHardcore_8() {
        return hardcore_8;
    }

    public void setHardcore_8(int hardcore_8) {
        this.hardcore_8 = hardcore_8;
    }

    public int getHardcore_9() {
        return hardcore_9;
    }

    public void setHardcore_9(int hardcore_9) {
        this.hardcore_9 = hardcore_9;
    }

    public int getHardcore_10() {
        return hardcore_10;
    }

    public void setHardcore_10(int hardcore_10) {
        this.hardcore_10 = hardcore_10;
    }

    public int getTimeEasy() {return timeEasy; }

    public void setTimeEasy(int timeEasy) {
        this.timeEasy = timeEasy;
    }

    public int getTimeMedium() {
        return timeMedium;
    }

    public void setTimeMedium(int timeMedium) {
        this.timeMedium = timeMedium;
    }

    public int getTimeHard() {
        return timeHard;
    }

    public void setTimeHard(int timeHard) {
        this.timeHard = timeHard;
    }

    public int getTimeExtreme() {
        return timeExtreme;
    }

    public void setTimeExtreme(int timeExtreme) {
        this.timeExtreme = timeExtreme;
    }

    public int getTimeInsane() {
        return timeInsane;
    }

    public void setTimeInsane(int timeInsane) {
        this.timeInsane = timeInsane;
    }

    public int getFlagEasy() {
        return flagEasy;
    }

    public void setFlagEasy(int flagEasy) {
        this.flagEasy = flagEasy;
    }

    public int getFlagMedium() {
        return flagMedium;
    }

    public void setFlagMedium(int flagMedium) {
        this.flagMedium = flagMedium;
    }

    public int getFlagHard() {
        return flagHard;
    }

    public void setFlagHard(int flagHard) {
        this.flagHard = flagHard;
    }

    public int getFlagExtreme() {
        return flagExtreme;
    }

    public void setFlagExtreme(int flagExtreme) {
        this.flagExtreme = flagExtreme;
    }

    public int getFlagInsane() {
        return flagInsane;
    }

    public void setFlagInsane(int flagInsane) {
        this.flagInsane = flagInsane;
    }

    public int getCountryEasy() {
        return countryEasy;
    }

    public void setCountryEasy(int countryEasy) {
        this.countryEasy = countryEasy;
    }

    public int getCountryMedium() {
        return countryMedium;
    }

    public void setCountryMedium(int countryMedium) {
        this.countryMedium = countryMedium;
    }

    public int getCountryHard() {
        return countryHard;
    }

    public void setCountryHard(int countryHard) {
        this.countryHard = countryHard;
    }

    public int getCountryExtreme() {
        return countryExtreme;
    }

    public void setCountryExtreme(int countryExtreme) {
        this.countryExtreme = countryExtreme;
    }

    public int getCountryInsane() {
        return countryInsane;
    }

    public void setCountryInsane(int countryInsane) {
        this.countryInsane = countryInsane;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
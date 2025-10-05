package com.example.test2.model;

public class ActivityItem {
    private final String name;
    private final double kcalPerHour;

    public ActivityItem(String name, double kcalPerHour) {
        this.name = name;
        this.kcalPerHour = kcalPerHour;
    }
    public String getName() { return name; }
    public double getKcalPerHour() { return kcalPerHour; }
}

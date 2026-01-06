package com.example.m_hike.Model;

public class HikeList {
    private int id, parking;
    private String name;
    private String location;
    private String date;
    private String length;
    private String difficult;
    private String description, weather, trail;
    private int wishlist;

    public HikeList(int id, String name, String location,
                    String date, String length, int parking, String weather, String trail,
                    String difficult, String description, int wishlist) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.length = length;
        this.parking = parking;
        this.weather = weather;
        this.trail = trail;
        this.difficult = difficult;
        this.description = description;
        this.wishlist = wishlist;
    }


    public int getId() { return id; }
    public int getParking() { return parking; }
    public String getWeather() { return weather; }
    public String getTrail() { return trail; }
    public String getName() { return name; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    public String getLength() { return length; }
    public String getDifficult() { return difficult; }
    public String getDescription() { return description; }

    public int getWishlist() { return wishlist; }
    public void setWishlist(int wishlist) { this.wishlist = wishlist; }

    public void setId(int id) { this.id = id; }
}

package com.example.ueefoodprotectionapp;

public class PostModel {

    private String contact, foodItem, quantity, location, expDate, price;
    private String foodImage;

    public PostModel() {
    }

    public PostModel(String contact, String foodItem, String quantity, String location, String foodImage, String expDate, String price) {
        this.contact = contact;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.location = location;
        this.foodImage = foodImage;
        this.expDate = expDate;
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) {
        this.foodItem = foodItem;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(String foodImage) {
        this.foodImage = foodImage;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

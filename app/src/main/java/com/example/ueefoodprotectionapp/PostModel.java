package com.example.ueefoodprotectionapp;

public class PostModel {

    private String foodId, foodItem, quantity, location;
    private String foodImage;

    public PostModel() {
    }

    public PostModel(String foodId, String foodItem, String quantity, String location, String foodImage) {
        this.foodId = foodId;
        this.foodItem = foodItem;
        this.quantity = quantity;
        this.location = location;
        this.foodImage = foodImage;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
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
}

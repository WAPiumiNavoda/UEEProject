package com.example.ueefoodprotectionapp;

public class HomeFoodMainModel {

    String amount , bDate , imgUrl , item , type ;

    HomeFoodMainModel(){

    }

    public HomeFoodMainModel(String amount, String bDate, String imgUrl, String item, String type) {
        this.amount = amount;
        this.bDate = bDate;
        this.imgUrl = imgUrl;
        this.item = item;
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

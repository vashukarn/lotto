package com.hpt.lottoadmin;

public class SalesForRecyclerView {
    String name;
    String email;
    String amountspent;
    String quantity;
    String price;

    public SalesForRecyclerView(){
        // Empty Constructor Needed Not to be removed!!
    }
    public SalesForRecyclerView(String name, String email, String amountspent, String quantity, String price){
        this.name = name;
        this.email = email;
        this.amountspent = amountspent;
        this.quantity = quantity;
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAmountspent() {
        return amountspent;
    }

    public String getQuantity() {
        return quantity;
    }
}

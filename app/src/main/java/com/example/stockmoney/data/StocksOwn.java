package com.example.stockmoney.data;

public class StocksOwn {
    double avgPrice;
    String name;
    int quantity;
    String symbol;
    double currentPrice;

    public StocksOwn(){}

    public StocksOwn(double avgPrice, String name, int quantity, String symbol){
        this.avgPrice = avgPrice;
        this.name = name;
        this.quantity = quantity;
        this.symbol = symbol;
    }

    public void setAvgPrice(double avgPrice){
        this.avgPrice = avgPrice;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public void setCurrentPrice(double currentPrice) { this.currentPrice = currentPrice; }

    public double getAvgPrice(){
        return this.avgPrice;
    }

    public String getName(){
        return this.name;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public double getCurrentPrice() { return this.currentPrice; }

}
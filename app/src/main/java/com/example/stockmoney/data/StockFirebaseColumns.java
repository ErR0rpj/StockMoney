package com.example.stockmoney.data;

//This class is the columns details of firebase database. It used in adpter class to create list.
public class StockFirebaseColumns {

    private double price;
    //private double high; TODO: Add these whenever required. Just remove the quotes and make new...
    //TODO: ...constructor, getter and setter. Do not change the order.
    //private double low;
    private String chg;
    private String chg_percent;
    //private String dateTime;
    private String symbol;
    private int id;
    private String name;

    public StockFirebaseColumns(){}

    //Upgrade this to fetch more information from Firebase database.
    //This constructor is for fetching data from Firebase. This is in alphabetical order bcoz Firebase needs this
    public StockFirebaseColumns(String chg, String chg_percent, int id, String name, double price, String symbol) {
        this.price = price;
        this.chg = chg;
        this.chg_percent = chg_percent;
        this.symbol = symbol;
        this.id = id;
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setChg(String chg) {
        this.chg = chg;
    }

    public void setChg_percent(String chg_percent) {
        this.chg_percent = chg_percent;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public String getChg() {
        return chg;
    }

    public String getChg_percent() {
        return chg_percent;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

package com.example.stockmoney;

public class stockmodel {
    private String price,high,low,chg,chg_percent,dateTime,symbol,id;

    public stockmodel() {
    }

    public stockmodel(String price,String high,String low,String chg,String chg_percent,String dateTime,String symbol,String id) {

        this.price = price;
        this.high = high;
        this.low = low;
        this.chg = chg;
        this.chg_percent = chg_percent;
        this.dateTime = dateTime;
        this.symbol = symbol;
        this.id = id;

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getChg() {
        return chg;
    }

    public void setChg(String chg) {
        this.chg = chg;
    }

    public String getChg_percent() {
        return chg_percent;
    }

    public void setChg_percent(String chg_percent) {
        this.chg_percent = chg_percent;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

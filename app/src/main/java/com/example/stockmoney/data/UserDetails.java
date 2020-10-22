package com.example.stockmoney.data;

import co.example.stockmoney.StocksOwn;

public class UserDetails {
    private String email;
    private String username;
    private int funds;
    private StocksOwn stocksOwn;

    public UserDetails(){}

    public UserDetails(String email, int funds, StocksOwn stocksOwn, String username) {
        this.email = email;
        this.funds = funds;
        this.stocksOwn = stocksOwn;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFunds(int funds) {
        this.funds = funds;
    }

    public void setStocksOwn(StocksOwn stocksOwn){
        this.stocksOwn = stocksOwn;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getFunds() {
        return funds;
    }

    public StocksOwn getStocksOwn(){
        return stocksOwn;
    }
}

package com.example.stockmoney.data;

public class UserDetails {
    private String email;
    private String username;
    private double funds;
    private int rank;
    private StocksOwn stocksOwn;
    private String uid;

    public UserDetails(){}

    public UserDetails(String email, double funds, int rank, StocksOwn stocksOwn, String username) {
        this.email = email;
        this.funds = funds;
        this.rank = rank;
        this.stocksOwn = stocksOwn;
        this.username = username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setStocksOwn(StocksOwn stocksOwn){
        this.stocksOwn = stocksOwn;
    }

    public void setUid(String uid){
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public double getFunds() {
        return funds;
    }

    public int getRank() {
        return rank;
    }

    public String getUid(){
        return uid;
    }

    public StocksOwn getStocksOwn(){
        return stocksOwn;
    }
}

package com.example.stockmoney.data;

public class UserDetails {
    private String email;
    private String username;
    private int funds;

    public UserDetails(){}

    public UserDetails(String email, int funds, String username) {
        this.email = email;
        this.funds = funds;
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

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getFunds() {
        return funds;
    }
}

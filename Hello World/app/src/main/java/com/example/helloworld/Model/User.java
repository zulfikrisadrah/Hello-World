package com.example.helloworld.Model;

public class User {

    private int id;
    private int life;
    private int coin;
    private int level_capital;
    private int level_currency;

    public User(int id, int life, int coin, int level_capital, int level_currency) {
        this.id = id;
        this.life = life;
        this.coin = coin;
        this.level_capital = level_capital;
        this.level_currency = level_currency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getLevelCapital() {
        return level_capital;
    }

    public void setLevelCapital(int level_capital) {
        this.level_capital = level_capital;
    }

    public int getLevelCurrency() {
        return level_currency;
    }

    public void setLevelCurrency(int level_currency) {
        this.level_currency = level_currency;
    }
}

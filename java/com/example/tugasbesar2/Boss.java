package com.example.tugasbesar2;

public class Boss {
    private int bossPosX;
    private int bossPosY;
    private int bossHealth;

    public Boss(int bossPosX, int bossPosY) {
        this.bossPosX = bossPosX;
        this.bossPosY = bossPosY;
        this.bossHealth = 5;
    }

    public int getBossPosX() {
        return bossPosX;
    }

    public void setBossPosX(int bossPosX) {
        this.bossPosX = bossPosX;
    }

    public int getBossPosY() {
        return bossPosY;
    }

    public void setBossPosY(int bossPosY) {
        this.bossPosY = bossPosY;
    }

    public int getBossHealth() {
        return bossHealth;
    }

    public void setBossHealth(int bossHealth) {
        this.bossHealth = bossHealth;
    }
}

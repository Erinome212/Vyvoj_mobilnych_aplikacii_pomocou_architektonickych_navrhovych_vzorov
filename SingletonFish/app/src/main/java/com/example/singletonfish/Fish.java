package com.example.singletonfish;

public class Fish {
    private static Fish INSTANCE = null;
    private int positionx;
    private int positiony;
    private int speed;

    private Fish() {
    }
// tu mozu byt
    public static Fish getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Fish();
        }
        return INSTANCE;
    }

    public void setPositionx(int positionx) {
        this.positionx = positionx;
    }

    public void setPositiony(int positiony) {
        this.positiony = positiony;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPositionx() {
        return positionx;
    }

    public int getPositiony() {
        return positiony;
    }

    public int getSpeed() {
        return speed;
    }
}

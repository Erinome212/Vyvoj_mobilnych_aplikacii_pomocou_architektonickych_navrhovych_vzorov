package com.example.fishbuilder;

public class Fish {
    private int positionx;
    private int positiony;
    private int speed;
    private int score;
    private int life;

    public int getPositionx() {
        return positionx;
    }

    public int getPositiony() {
        return positiony;
    }

    public int getSpeed() {
        return speed;
    }

    public int getScore() {
        return score;
    }

    public int getLife() {
        return life;
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

    public void setScore(int score) {
        this.score = score;
    }

    public void setLife(int life) {
        this.life = life;
    }

    private Fish(FishBuilder builder) {
        this.positionx = builder.positionx;
        this.positiony = builder.positiony;
        this.speed = builder.speed;
        this.score = builder.score;
        this.life = builder.life;
    }

    public static class FishBuilder {
        private int positionx;
        private int positiony;
        private int speed;
        private int score;
        private int life;

        public FishBuilder(int speed) {
            this.speed = speed;
        }

        public FishBuilder setPositionx(int positionx) {
            this.positionx = positionx;
            return this;
        }

        public FishBuilder setPositiony(int positiony) {
            this.positiony = positiony;
            return this;
        }

        public FishBuilder setScore(int score) {
            this.score = score;
            return this;
        }

        public FishBuilder setLife(int life) {
            this.life = life;
            return this;
        }

        public Fish build() {
            return new Fish(this);
        }
    }
}

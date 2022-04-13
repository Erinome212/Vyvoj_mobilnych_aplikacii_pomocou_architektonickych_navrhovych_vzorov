package com.example.factoryfish;

public class EntityFactory {
    public static Entity getEntity(String type) {
        if ("red".equalsIgnoreCase(type)) return new Red(0, 30, 25);
        else if ("yellow".equalsIgnoreCase(type)) return new Yellow(-100, 100, 16);
        else if ("green".equalsIgnoreCase(type)) return new Green(0, 25, 12);
        else if ("fish".equalsIgnoreCase(type)) return new Fish(5, 40, 500);

        return null;
    }
}

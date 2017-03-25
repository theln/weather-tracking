package com.weather.sensor;

import java.util.Random;

public class Utils {
    public static float getRandomValue(float min, float max) {
        Random rand = new Random();
        float value = rand.nextFloat() * (max - min) + min;

        return value;
    }
}

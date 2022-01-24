package tools;

import java.security.SecureRandom;
import java.util.Random;

public class RandomNumersInRange {

    private static Random randomGenerator = new SecureRandom();

    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return randomGenerator.nextInt((max - min) + 1) + min;
    }

}

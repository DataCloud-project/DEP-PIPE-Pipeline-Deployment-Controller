package eu.ubitech.utils;


import java.util.Random;

public class RandomHexIDGenerator {

    public static String generateRandomHexID(int length) {
        StringBuilder hexIDBuilder = new StringBuilder();
        Random random = new Random();
        String hexChars = "abcdefghijkmnpqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ1234567890";

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(hexChars.length());
            char randomHexChar = hexChars.charAt(randomIndex);
            hexIDBuilder.append(randomHexChar);
        }

        return hexIDBuilder.toString();
    }
}


package com.gerasimchuk.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * The type Personal number generator.
 */
@Service
public class PersonalNumberGenerator {
    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PersonalNumberGenerator.class);
    /**
     * Generate string.
     *
     * @param length the length
     * @return the string
     */
    public static String generate(int length){
        LOGGER.info("Class: " + PersonalNumberGenerator.class.getName() + " method: generate");
        if (length < 1 || length > 10) return null;
        StringBuilder stringBuilder = new StringBuilder("");
        for(int i = 0; i < length; i++){
            stringBuilder.append(new Random().nextInt(10));
        }
        LOGGER.info("Class: " + PersonalNumberGenerator.class.getName() + " out from method: generate");
        return stringBuilder.toString();
    }
}

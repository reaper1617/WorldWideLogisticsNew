package com.gerasimchuk.utils;

/**
 * The type Date parser.
 */
public class DateParser {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(DateParser.class);
    // Tue Sep 18 02:35:39 MSK 2018
    // Ddd MMM dd hh:mm:ss z yyyy


    /**
     * Parse year int.
     *
     * @param s the s
     * @return the int
     */
    public static int parseYear(String s) {
        LOGGER.info("Class: " + DateParser.class.getName() + " method: parseYear");
        String sYear = s.substring(24,28);
        int year = 0;
        try{
            year = Integer.parseInt(sYear);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        LOGGER.info("Class: " + DateParser.class.getName() + " out from method: parseYear");
        return year;
    }

    /**
     * Parse month int.
     *
     * @param s the s
     * @return the int
     */
    public static int parseMonth(String s){
        LOGGER.info("Class: " + DateParser.class.getName() + " method: parseMonth");
        String month =  s.substring(4,7);
        if (month.equals("Jan")) return 0;
        if (month.equals("Feb")) return 1;
        if (month.equals("Mar")) return 2;
        if (month.equals("Apr")) return 3;
        if (month.equals("May")) return 4;
        if (month.equals("Jun")) return 5;
        if (month.equals("Jul")) return 6;
        if (month.equals("Aug")) return 7;
        if (month.equals("Sep")) return 8;
        if (month.equals("Oct")) return 9;
        if (month.equals("Nov")) return 10;
        if (month.equals("Dec")) return 11;
        LOGGER.info("Class: " + DateParser.class.getName() + " out from method: parseMonth");
        return 0;
    }


    /**
     * Parse day int.
     *
     * @param s the s
     * @return the int
     */
    public static int parseDay(String s){
        LOGGER.info("Class: " + DateParser.class.getName() + " method: parseDay");
        String sDay =  s.substring(8,10);
        int day = 0;
        try{
            day = Integer.parseInt(sDay);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        if (day == 0) return 0;
        LOGGER.info("Class: " + DateParser.class.getName() + " out from method: parseDay");
        return day;
    }


    /**
     * Parse hour int.
     *
     * @param s the s
     * @return the int
     */
    public static int parseHour(String s) {
        LOGGER.info("Class: " + DateParser.class.getName() + " method: parseHour");
        String sHour = s.substring(11,13);
        int hour = 0;
        try {
            hour = Integer.parseInt(sHour);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        LOGGER.info("Class: " + DateParser.class.getName() + " out from method: parseHour");
        return hour;
    }


    /**
     * Parse minutes int.
     *
     * @param s the s
     * @return the int
     */
    public static int parseMinutes(String s) {
        LOGGER.info("Class: " + DateParser.class.getName() + " method: parseMinutes");
        String sMinutes = s.substring(14,16);
        int minutes = 0;
        try {
            minutes = Integer.parseInt(sMinutes);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        LOGGER.info("Class: " + DateParser.class.getName() + " out from method: parseMinutes");
        return minutes;
    }

    /**
     * Parse seconds int.
     *
     * @param s the s
     * @return the int
     */
    public static int parseSeconds(String s){
        LOGGER.info("Class: " + DateParser.class.getName() + " method: parseSeconds");
        String sSeconds = s.substring(17,19);
        int seconds = 0;
        try{
            seconds = Integer.parseInt(sSeconds);
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        LOGGER.info("Class: " + DateParser.class.getName() + " out from method: parseSeconds");
        return seconds;
    }
}

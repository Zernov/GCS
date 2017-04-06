package agents;

import java.util.Random;
import java.util.TreeMap;

public final class Global {

    public final static Integer TIMEZONE_SIZE = 5;
    public final static Integer TIMEZONE_COUNT = 6;
    public final static Integer LISTENER_COUNT = 20;
    public final static Integer SCHEDULE_COUNT = 10;
    public final static Integer TOTAL = TIMEZONE_SIZE * TIMEZONE_COUNT;
    private final static Double min = 25.0;
    private final static Double max = 28.0;
    private final static Integer from = 1;
    private final static Integer to = 2;
    private final static Double border = 100.0;

    public static Double getRandomDouble() { return min + (new Random().nextDouble() * (max - min)); }
    public static Integer getRandomInteger() { return from + (new Random().nextInt((to - from + 1))); }

    //Profit Value
    public static Integer getDistributionValue(Integer x, Double a, Integer b) {
        Double temp_top = Math.cbrt(x/a - 1);
        Double temp_bot = Math.cbrt(TOTAL.doubleValue()/a - 1);
        for (int i = from ; i < b; i++) {
            temp_top = Math.cbrt(temp_top);
            temp_bot = Math.cbrt(temp_bot);
        }
        Double result = border * ((temp_top + 1) / (temp_bot + 1));
        return result.intValue();
    }

    //====================Map (Listener)====================//
    //String -> Map
    public static TreeMap<Integer, Integer> toMap(String string) {
        TreeMap<Integer, Integer> result = new TreeMap<>();
        String[] string_split = string.split(" ");
        if (string_split.length == TOTAL << 1) {
            for (int i = 0; i < TOTAL << 1; ) {
                result.put(Integer.parseInt(string_split[i++]), Integer.parseInt(string_split[i++]));
            }
            return result;
        } else {
            return null;
        }
    }

    //Map -> String
    public static String toStringMessage(TreeMap<Integer, Integer> preferences) {
        String result = "";
        for (Integer report : preferences.keySet()) {
            result = result + String.format("%s %s ", report.toString(), preferences.get(report));
        }
        return result.substring(0, result.length() - 1);
    }

    //Map -> Console
    public static String toStringConsole(TreeMap<Integer, Integer> preferences) {
        String result = "Report\tProfit\n";
        for (Integer report : preferences.keySet()) {
            result = result + String.format("%s\t\t%s\n", report.toString(), preferences.get(report));
        }
        return result;
    }
    //======================================================//

    //===================Array (Schedule)===================//
    //String -> Array
    public static Integer[][] toArray(String string) {
        Integer[][] result = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];
        String[] string_split = string.split(" ");
        if (string_split.length == TOTAL) {
            for (int i = 0; i < TIMEZONE_COUNT; i++) {
                for (int j = 0; j < TIMEZONE_SIZE; j++) {
                    result[i][j] = Integer.parseInt(string_split[i * TIMEZONE_SIZE + j]);
                }
            }
            return  result;
        } else {
            return null;
        }
    }

    //Array -> String
    public static String toStringMessage(Integer[][] schedule) {
        String result = "";
        for (Integer[] timezone : schedule) {
            for(Integer report : timezone) {
                result = result + String.format("%s ", report.toString());
            }
        }
        return result.substring(0, result.length() - 1);
    }

    //Array -> Console
    public static String toStringConsole(Integer[][] schedule) {
        String result = "Schedule\n";
        for (Integer[] timezone : schedule) {
            for (Integer report : timezone) {
                result = result + String.format("%s\t", report.toString());
            }
            result = result + "\n";
        }
        return result;
    }
    //======================================================//

    //=========================Help=========================//
    //Array Sum
    public static Integer[][] sumArray(Integer[][] a, Integer[][] b) {
        Integer[][] result = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];
        for (int i = 0; i < TIMEZONE_COUNT; i++) {
            for (int j = 0; j < TIMEZONE_SIZE; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    //Total Sum
    public static Integer sumTotal(Integer[][] array) {
        Integer result = 0;
        for (int i = 0; i < TIMEZONE_COUNT; i++) {
            for (int j = 0; j < TIMEZONE_SIZE; j++) {
                result = result + array[i][j];
            }
        }
        return result;
    }
    //======================================================//
}

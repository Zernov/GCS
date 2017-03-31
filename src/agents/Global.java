package agents;

import java.util.TreeMap;

public final class Global {

    public final static Integer TIMEZONE_SIZE = 3;
    public final static Integer TIMEZONE_COUNT = 2;
    public final static Integer LISTENER_COUNT = 100;
    public final static Integer TOTAL = TIMEZONE_SIZE * TIMEZONE_COUNT;

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
            for(Integer report : timezone) {
                result = result + String.format("%s\t", report.toString());
            }
            result = result + "\n";
        }
        return result;
    }
    //======================================================//
}

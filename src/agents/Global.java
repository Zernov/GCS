package agents;

import jade.core.AID;
import javafx.util.Pair;

import java.util.*;

public final class Global {

    //Main Settings
    public final static Integer TIMEZONE_SIZE = 2;
    public final static Integer TIMEZONE_COUNT = 15;
    public final static Integer LISTENER_COUNT = 100;
    public final static Integer SCHEDULE_COUNT = 5;
    public final static Integer TOTAL = TIMEZONE_SIZE * TIMEZONE_COUNT;

    //Generation Settings
    public final static Integer GENERATION_COUNT = 20;
    public final static Integer CLONE_COUNT = 1;
    public final static Integer MUTANTE_COUNT = 1;
    public final static Integer MUTATIONS_COUNT = 5;
    public final static Integer MALE_COUNT = 3;
    public final static Integer TOP = 3;

    //Profit Distribution Settings
    private final static Double min = 20.0;
    private final static Double max = 27.0;
    private final static Integer from = 1;
    private final static Integer to = 2;
    private final static Double border = 300.0;

    public static Integer PROFIT_START = 0;
    public static Integer PROFIT_END = 0;

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
        String result = "Key\tValue\n";
        for (Integer report : preferences.keySet()) {
            result = result + String.format("%s\t%s\n", report.toString(), preferences.get(report));
        }
        return result;
    }

    //String -> ArrayListArray
    public static ArrayList<Integer[][]> toArrayList(String string) {
        ArrayList<Integer[][]> result = new ArrayList<>();
        String[] string_split = string.split("\n");
        if (string_split.length == SCHEDULE_COUNT) {
            for (int i = 0; i < string_split.length; i++) {
                Integer[][] temp = toArray(string_split[i]);
                result.add(temp);
            }
            return  result;
        } else {
            return null;
        }
    }

    //Array -> String
    public static String toStringMessageList(ArrayList<Integer[][]> arraylist) {
        String result = "";
        for (Integer[][] array : arraylist) {
            result = result + String.format("%s\n", toStringMessage(array));
        }
        return result.substring(0, result.length() - 1);
    }

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
        String result = "";
        for (Integer[] timezone : schedule) {
            for (Integer report : timezone) {
                result = result + String.format("%s\t", report.toString());
            }
            result = result + "\n";
        }
        return result;
    }

    //String -> Array
    public static Integer[] toArrayTop(String string) {
        Integer[] result = new Integer[TOP];
        String[] string_split = string.split(" ");
        if (string_split.length == TOP) {
            for (int i = 0; i < TOP; i++) {
                result[i] = Integer.parseInt(string_split[i]);
            }
            return result;
        } else {
            return null;
        }
    }

    //Array -> String
    public static String toStringMessage(Integer[] top) {
        String result = "";
        for (Integer report: top) {
            result = result + String.format("%s ", report.toString());
        }
        return result.substring(0, result.length() - 1);
    }

    //Array -> Console
    public static String toStringConsole(Integer[] top) {
        String result = "";
        for (Integer report: top) {
            result = result + String.format("%s\t", report.toString());
        }
        return result.substring(0, result.length() - 1);
    }

    //String -> Array
    public static ArrayList<Integer> toArrayPair(String string) {
        ArrayList<Integer> result = new ArrayList<>();
        String[] string_split = string.split(" ");
        for (int i = 0; i < string_split.length; i++) {
            result.add(Integer.parseInt(string_split[i]));
        }
        return result;
    }

    //Array -> String
    public static String toStringMessage(ArrayList<Integer> pair) {
        String result = "";
        for (Integer report: pair) {
            result = result + String.format("%s ", report.toString());
        }
        return result.substring(0, result.length() - 1);
    }

    //Array -> Console
    public static String toStringConsole(ArrayList<Integer> pair) {
        String result = "";
        for (Integer report: pair) {
            result = result + String.format("%s\t", report.toString());
        }
        return result.substring(0, result.length() - 1);
    }

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

    //Min
    private static int minIndex(Integer[] array) {
        int result = -1;
        Integer min = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
                result = i;
            }
        }
        return  result;
    }

    //Top Reports
    public static Integer[] topReports(Integer[][] schedule, Integer[][] profit) {
        Integer[] result = new Integer[TOP];
        Integer[] temp = new Integer[TOP];
        for (int i = 0; i < TOP; i++) {
            result[i] = 0;
            temp[i] = 0;
        }
        for (int i = 0; i < TIMEZONE_COUNT; i++) {
            for (int j = 0; j < TIMEZONE_SIZE; j++) {
                int min = minIndex(temp);
                if (temp[min] < profit[i][j]) {
                    temp[min] = new Integer(profit[i][j]);
                    result[min] = new Integer(schedule[i][j]);
                }
            }
        }
        return result;
    }

    //Top Sums
    public static AID[] topSums(HashMap<AID, Integer> sums) {
        AID[] result = new AID[CLONE_COUNT];
        Integer[] temp = new Integer[CLONE_COUNT];
        for (int i = 0; i < CLONE_COUNT; i++) {
            temp[i] = 0;
        }
        for (Map.Entry<AID, Integer> sum: sums.entrySet()) {
            int min = minIndex(temp);
            if (temp[min] < sum.getValue()) {
                temp[min] = new Integer(sum.getValue());
                result[min] = sum.getKey();
            }
        }
        return result;
    }

    //Top Sum
    public static AID topSum(HashMap<AID, Integer> sums) {
        AID result = null;
        Integer temp = 0;
        for (Map.Entry<AID, Integer> sum: sums.entrySet()) {
            if (temp < sum.getValue()) {
                temp = new Integer(sum.getValue());
                result = sum.getKey();
            }
        }
        return result;
    }

    //Top Tops
    public static AID topTops(HashMap<AID, ArrayList<Integer>> sums) {
        AID result = null;
        Integer temp = 0;
        for (Map.Entry<AID, ArrayList<Integer>> sum: sums.entrySet()) {
            if (temp < sum.getValue().size()) {
                temp = new Integer(sum.getValue().size());
                result = sum.getKey();
            }
        }
        return result;
    }

    //Close Arrays
    public static ArrayList<Integer> closeArrays(Integer[] a, Integer[] b) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            result.add(a[i]);
        }
        for (int i = 0; i < b.length; i++) {
            boolean flag = true;
            for (int j = 0; j < a.length; j++) {
                if (b[i].equals(a[j])) {
                    flag = false;
                }
            }
            if (flag) {
                result.add(b[i]);
            }
        }
        return result;
    }

    //Merge Array
    public static Integer[][] createArray(ArrayList<Integer> top) {
        Integer[][] result = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];
        ArrayList<Integer> full = new ArrayList<>();
        for (int i = 0; i < top.size(); i++) {
            full.add(top.get(i));
        }
        Collections.shuffle(full);
        ArrayList<Integer> complete = completeArray(top);
        Collections.shuffle(complete);
        for (int i = 0; i < complete.size(); i++) {
            full.add(complete.get(i));
        }
        for (int i = 0; i < TIMEZONE_SIZE; i++) {
            for (int j = 0; j < TIMEZONE_COUNT; j++) {
                result[j][i] = new Integer(full.get(i * TIMEZONE_COUNT + j));
            }
        }
        return result;
    }

    //Clone Array
    public static Integer[][] cloneArray(Integer[][] array) {
        Integer[][] result = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < TIMEZONE_COUNT; i++) {
            for (int j = 0; j < TIMEZONE_SIZE; j++) {
                result[i][j] = new Integer(array[i][j]);
            }
        }
        return result;
    }

    //Mutate Array
    public static Integer[][] mutateArray(Integer[][] array) {
        Integer[][] result = new Integer[TIMEZONE_COUNT][TIMEZONE_SIZE];
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < TIMEZONE_COUNT; i++) {
            for (int j = 0; j < TIMEZONE_SIZE; j++) {
                result[i][j] = new Integer(array[i][j]);
            }
        }
        for (int i = 0; i < TOTAL; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
        for (int i = 0; i < MUTATIONS_COUNT; i++) {
            Integer a = list.get(2 * i);
            Integer a_i = a / TIMEZONE_SIZE;
            Integer a_j = a % TIMEZONE_SIZE;
            Integer b = list.get(2 * i + 1);
            Integer b_i = b / TIMEZONE_SIZE;
            Integer b_j = b % TIMEZONE_SIZE;
            Integer temp = new Integer(array[a_i][a_j]);
            result[a_i][a_j] = new Integer(array[b_i][b_j]);
            result[b_i][b_j] = new Integer(temp);
        }
        return result;
    }

    //In Array
    public static boolean inArray(AID[] array, AID aid) {
        for (int i = 0; i < array.length; i++) {
            if (aid.equals(array[i])) {
                return true;
            }
        }
        return false;
    }

    //In Array
    public static boolean inArray(ArrayList<Integer> array, Integer aid) {
        for (int i = 0; i < array.size(); i++) {
            if (aid.equals(array.get(i))) {
                return true;
            }
        }
        return false;
    }

    //Complete Array
    public static ArrayList<Integer> completeArray(ArrayList<Integer> array) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < TOTAL; i++) {
            Integer temp = new Integer(i);
            if (!inArray(array, temp)) {
                result.add(temp);
            }
        }
        return result;
    }
}

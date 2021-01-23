package com.nesaak.kolical.util;

public class TimeUtil {

    public static long getTime() {
        return System.currentTimeMillis();
    }

    public static String getShortRepresentation(long time, int places) {
        time = Math.abs(time);
        int count = 0;
        StringBuilder result = new StringBuilder();
        for (TimeValue unit : TimeValue.values()) {
            if (unit.getValue() < time) {
                long num = time / unit.getValue();
                time = time % unit.getValue();
                result.append(num + unit.getSymbol() + " ");
                count++;
                if (count >= places) return result.toString().replaceFirst(",", "");
            }
        }
        return result.toString().trim();
    }

    public static String getRepresentation(long time, int places) {
        time = Math.abs(time);
        int count = 0;
        StringBuilder result = new StringBuilder();
        for (TimeValue unit : TimeValue.values()) {
            if (unit.getValue() < time) {
                long num = time / unit.getValue();
                time = time % unit.getValue();
                if (num == 1) result.append(", " + num + " " + unit.getName());
                else result.append(", " + num + " " + unit.getNamePlural());
                count++;
                if (count >= places) return result.toString().replaceFirst(",", "");
            }
        }
        return result.toString().replaceFirst(", ", "");
    }

    public enum TimeValue {
        YEAR(31556952000L, "y"),
        MONTH(2592000000L, "mo"),
        WEEK(604800000, "w"),
        DAY(86400000, "d"),
        HOUR(3600000, "h"),
        MINUTE(60000, "m"),
        SECOND(1000, "s"),
        MILISECOND(1, "ms");

        private long value;
        private String symbol;

        TimeValue(long value, String symbol) {
            this.value = value;
            this.symbol = symbol;
        }

        public long getValue() {
            return value;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getName() {
            return name().toLowerCase();
        }

        public String getNamePlural() {
            return getName() + "s";
        }
    }

}

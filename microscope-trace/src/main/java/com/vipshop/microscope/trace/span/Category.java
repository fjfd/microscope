package com.vipshop.microscope.trace.span;

import com.vipshop.microscope.trace.gen.Span;

/**
 * Category of span type.
 *
 * @author Xu Fei
 * @version 1.0
 */
public enum Category {

    URL {

        int[] zone = {500, 1000, 1500, 2000, 3000, 5000};

        @Override
        public int intValue() {
            return 1;
        }

        @Override
        public String strValue() {
            return "URL";
        }

        @Override
        public boolean hasProblem(int time) {
            return time > zone[0];
        }

        @Override
        public int getTimeZone(int time) {
            if (time >= zone[0] && time < zone[1]) {
                return 1;
            } else if (time >= zone[1] && time < zone[2]) {
                return 2;
            } else if (time >= zone[2] && time < zone[3]) {
                return 3;
            } else if (time >= zone[3] && time < zone[4]) {
                return 4;
            } else if (time >= zone[4] && time < zone[5]) {
                return 5;
            } else {
                return 6;
            }
        }
    },

    Action {

        int[] zone = {50, 100, 200, 300, 500, 1000};

        @Override
        public int intValue() {
            return 2;
        }

        @Override
        public String strValue() {
            return "Action";
        }

        @Override
        public boolean hasProblem(int time) {
            return time > zone[0];
        }

        @Override
        public int getTimeZone(int time) {
            if (time >= zone[0] && time < zone[1]) {
                return 1;
            } else if (time >= zone[1] && time < zone[2]) {
                return 2;
            } else if (time >= zone[2] && time < zone[3]) {
                return 3;
            } else if (time >= zone[3] && time < zone[4]) {
                return 4;
            } else if (time >= zone[4] && time < zone[5]) {
                return 5;
            } else {
                return 6;
            }
        }
    },

    Service {
        int[] zone = {50, 100, 200, 300, 500, 1000};

        @Override
        public int intValue() {
            return 3;
        }

        @Override
        public String strValue() {
            return "Service";
        }

        @Override
        public boolean hasProblem(int time) {
            return time > zone[0];
        }

        @Override
        public int getTimeZone(int time) {
            if (time >= zone[0] && time < zone[1]) {
                return 1;
            } else if (time >= zone[1] && time < zone[2]) {
                return 2;
            } else if (time >= zone[2] && time < zone[3]) {
                return 3;
            } else if (time >= zone[3] && time < zone[4]) {
                return 4;
            } else if (time >= zone[4] && time < zone[5]) {
                return 5;
            } else {
                return 6;
            }
        }

    },

    DB {

        int[] zone = {10, 20, 30, 40, 60, 100};

        @Override
        public int intValue() {
            return 4;
        }

        @Override
        public String strValue() {
            return "DB";
        }

        @Override
        public boolean hasProblem(int time) {
            return time > zone[0];
        }

        @Override
        public int getTimeZone(int time) {
            if (time >= zone[0] && time < zone[1]) {
                return 1;
            } else if (time >= zone[1] && time < zone[2]) {
                return 2;
            } else if (time >= zone[2] && time < zone[3]) {
                return 3;
            } else if (time >= zone[3] && time < zone[4]) {
                return 4;
            } else if (time >= zone[4] && time < zone[5]) {
                return 5;
            } else {
                return 6;
            }
        }

    },

    Cache {
        int[] zone = {10, 20, 30, 40, 60, 100};

        @Override
        public int intValue() {
            return 5;
        }

        @Override
        public String strValue() {
            return "Cache";
        }

        @Override
        public boolean hasProblem(int time) {
            return time > zone[0];
        }

        @Override
        public int getTimeZone(int time) {
            if (time >= zone[0] && time < zone[1]) {
                return 1;
            } else if (time >= zone[1] && time < zone[2]) {
                return 2;
            } else if (time >= zone[2] && time < zone[3]) {
                return 3;
            } else if (time >= zone[3] && time < zone[4]) {
                return 4;
            } else if (time >= zone[4] && time < zone[5]) {
                return 5;
            } else {
                return 6;
            }
        }

    },

    Method {
        int[] zone = {50, 100, 150, 200, 300, 500};

        @Override
        public int intValue() {
            return 6;
        }

        @Override
        public String strValue() {
            return "Method";
        }

        @Override
        public boolean hasProblem(int time) {
            return time > zone[0];
        }

        @Override
        public int getTimeZone(int time) {
            if (time >= zone[0] && time < zone[1]) {
                return 1;
            } else if (time >= zone[1] && time < zone[2]) {
                return 2;
            } else if (time >= zone[2] && time < zone[3]) {
                return 3;
            } else if (time >= zone[3] && time < zone[4]) {
                return 4;
            } else if (time >= zone[4] && time < zone[5]) {
                return 5;
            } else {
                return 6;
            }
        }
    },

    System {
        int[] zone = {50, 100, 150, 200, 300, 500};

        @Override
        public int intValue() {
            return 7;
        }

        @Override
        public String strValue() {
            return "System";
        }

        @Override
        public boolean hasProblem(int time) {
            return time > zone[0];
        }

        @Override
        public int getTimeZone(int time) {
            if (time >= zone[0] && time < zone[1]) {
                return 1;
            } else if (time >= zone[1] && time < zone[2]) {
                return 2;
            } else if (time >= zone[2] && time < zone[3]) {
                return 3;
            } else if (time >= zone[3] && time < zone[4]) {
                return 4;
            } else if (time >= zone[4] && time < zone[5]) {
                return 5;
            } else {
                return 6;
            }
        }
    };

    public static int getIntValue(Span span) {
        String category = span.getSpanType();
        return Category.valueOf(category).intValue();
    }

    public static int getIntValue(String type) {
        return Category.valueOf(type).intValue();
    }

    public static String getStringValue(int value) {
        Category[] categories = Category.values();
        for (Category categorie : categories) {
            if (categorie.intValue() == value) {
                return categorie.strValue();
            }
        }
        return "not found";
    }

    public static boolean hasProblem(Span span) {
        String category = span.getSpanType();
        int time = span.getDuration();
        return Category.valueOf(category).hasProblem(time);
    }

    public static int getTimeZone(Span span) {
        String category = span.getSpanType();
        int time = span.getDuration();
        return Category.valueOf(category).getTimeZone(time);
    }

    abstract int intValue();

    abstract String strValue();

    abstract boolean hasProblem(int time);

    abstract int getTimeZone(int time);

    public int getIntValue() {
        return this.intValue();
    }

    public String getStrValue() {
        return this.strValue();
    }

    @Override
    public String toString() {
        return this.strValue();
    }

}

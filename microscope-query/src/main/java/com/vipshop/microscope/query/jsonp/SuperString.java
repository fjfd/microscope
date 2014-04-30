package com.vipshop.microscope.query.jsonp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: XuJianbo
 * Date: 2007-4-6
 * Time: 10:21:04
 * To change this template use File | Settings | File Templates.
 */

public class SuperString {
    public static final String EMPTY = "";
    public static final int NUMERIC_SHORT = 0;
    public static final int NUMERIC_INT = 1;
    public static final int NUMERIC_LONG = 2;
    public static final int NUMERIC_FLOAT = 3;
    public static final int NUMERIC_DOUBLE = 4;

    public static String notNull(String strTemp) {
        if (strTemp == null) {
            return "";
        } else {
            return strTemp;
        }
    }

    public static String notNullTrim(String strTemp) {
        return notNull(strTemp).trim();
    }

    public static boolean isBlank(String str) {
        return (notNullTrim(str).length() == 0);
    }

    public static String getQuoteDel(String strTemp) {
        return notNull(strTemp).replace("\"", "");
    }

    public static int getInt(String strTemp) {
        return getInt(strTemp, 0);
    }

    public static int getInt(String strTemp, int def) {
        strTemp = notNull(strTemp);
        if (strTemp.equals("")) {
            return def;
        }
        try {
            return (int) Math.floor(Double.parseDouble(strTemp));
        } catch (Exception e) {
            return def;
        }
    }

    public static long getLong(String strTemp) {
        return getLong(strTemp, 0L);
    }

    public static long getLong(String strTemp, long def) {
        strTemp = notNull(strTemp);
        if (strTemp.equals("")) {
            return def;
        }
        try {
            return (long) Math.floor(Double.parseDouble(strTemp));
        } catch (Exception e) {
            return def;
        }
    }

    public static float getFloat(String strTemp) {
        strTemp = notNull(strTemp);
        if (strTemp.equals("")) {
            return 0f;
        }
        try {
            return Float.parseFloat(strTemp);
        } catch (Exception e) {
            return 0f;
        }
    }

    public static double getDouble(String strTemp) {
        return getDouble(strTemp, 0D);
    }

    public static double getDouble(String strTemp, double defaultVal) {
        strTemp = notNull(strTemp);
        strTemp = strTemp.replace(",", "");
        if (strTemp.equals("")) {
            return 0d;
        }
        try {
            return Double.parseDouble(strTemp);
        } catch (Exception e) {
            return defaultVal;
        }
    }

    public static String getRandString(int minlen, int maxlen) {
        String s = "";
        if (minlen > maxlen) {
            minlen = maxlen;
        }
        for (; s.length() < maxlen; s = ("" + Math.random()).substring(2) + s) {
            ;
        }
        s = s.substring(s.length() - maxlen);
        int n = maxlen - minlen;
        if (n > 0) {
            n = (int) Math.round(n * Math.random());
        }
        if (n > 0) {
            s = s.substring(n);
        }
        return s;
    }

    /**
     * 判断字符串是否是数字
     *
     * @param str
     * @param intType
     * @return 是否为数字
     */
    @SuppressWarnings("unused")
    public static boolean isNumeric(String str, int intType) {
        try {
            switch (intType) {
                case NUMERIC_SHORT:
                    short shortN = Short.parseShort(str);
                    break;
                case NUMERIC_INT:
                    int intN = Integer.parseInt(str);
                    break;
                case NUMERIC_LONG:
                    long longN = Long.parseLong(str);
                    break;
                case NUMERIC_FLOAT:
                    float floatN = Float.parseFloat(str);
                    break;
                case NUMERIC_DOUBLE:
                    double doubleN = Double.parseDouble(str);
                    break;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 替换掉本网站模板文本中的变量${}
     *
     * @param text    要替换的文本全文
     * @param varname 变量名 $(name) name为变量名
     * @param value   替换的值
     * @return 替换后的文本
     */
    public static String replaceTemplateTag(String text, String varname, String value) {
        if (text == null) {
            return null;
        }
        return text.replace("${" + varname + "}", value);
    }

    @SuppressWarnings("rawtypes")
    public static String replaceTemplateTag(String text, Map map) {
        for (Object o : map.keySet()) {
            String key = (String) o;
            if (key != null && map.containsKey(key)) {
                text = replaceTemplateTag(text, key, (String) map.get(key));
            }
        }
        return text;
    }

    public static String clearText(String text) {
        return text.replace("'", "")
                .replace("\"", "")
                .replace("\r", "")
                .replace("\n", "")
                .replace("\t", "");
    }

    public static List<String> strToList(String str, String seperator) {
        List<String> list = new ArrayList<String>();
        if (str == null || str.length() == 0 || seperator == null) return list;
        list.addAll(Arrays.asList(str.split(seperator)));
        return list;
    }

}

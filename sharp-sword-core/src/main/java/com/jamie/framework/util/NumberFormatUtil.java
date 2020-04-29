package com.jamie.framework.util;

import java.math.BigDecimal;

/**
 * NumberFormatUtil
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/4/15 9:30
 */
public class NumberFormatUtil {

    /**
     * 小数点向上保留位数
     * @param value
     * @param scale
     * @param unit
     * @return
     */
    public static String formatRoundCeiling(Long value, int scale, UNIT unit) {
        return format(value, scale, unit, BigDecimal.ROUND_CEILING);
    }

    /**
     * 小数点四舍五入保留位数
     * @param value
     * @param scale
     * @param unit
     * @return
     */
    public static String formatRoundHalfUp(Long value, int scale, UNIT unit) {
        return format(value, scale, unit, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 小数点向下保留位数
     * @param value
     * @param scale
     * @param unit
     * @return
     */
    public static String formatRoundFloor(Long value, int scale, UNIT unit) {
       return format(value, scale, unit, BigDecimal.ROUND_FLOOR);
    }

    public static String format(Long value, int scale,  UNIT unit, int round) {
        if (value == null) {
            return "0" + (unit == null ? "" : unit.getDesc());
        }
        BigDecimal bigDecimal = new BigDecimal(value);
        return bigDecimal.divide(new BigDecimal(unit.getVal()), scale, round).toString() + unit.getDesc();
    }

    public enum UNIT {
        /**
         * 亿
         */
        HUNDRED_MILLION(100 * 1000000, "亿"),

        /**
         * 千万
         */
        TEN_MILLION(10 * 1000000, "千万"),

        /**
         * 百万
         */
        MILLION(1000000, "百万"),
        /**
         * 万
         */
        TEN_THOUSAND(10 * 1000, "万"),

        /**
         * 千
         */
        THOUSAND(1000, "千");

        private int val;
        private String desc;

        UNIT(int val, String desc) {
            this.val = val;
            this.desc = desc;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

}

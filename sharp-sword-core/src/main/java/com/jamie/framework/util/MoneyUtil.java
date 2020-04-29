package com.jamie.framework.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * MoneyUtil 钱数金额格式化
 *
 * @author lizheng
 * @version 1.0
 * @date 2020/4/9 14:32
 */
public class MoneyUtil {

    private static int TEN_THOUSAND = 10 * 1000;


    public static String removeZeroSuffix(String str) {
        if (str.endsWith(".0")) {
            return str.substring(0, str.lastIndexOf(".0"));
        } else {
            return str;
        }
    }


    /**
     * 格式化加单位后缀
     * @param cent
     * @return
     */
    public static String formatCentSuffixUnit(Integer cent) {
        return formatCent(cent, true);
    }

    /**
     * 格式化, 没有单位后缀
     * @param cent
     * @return
     */
    public static String formatCentNotSuffixUnit(Integer cent) {
        return formatCent(cent, false);
    }

    /**
     * <p>一万以下: ###,###.00 元
     * <p>一万以上: 1.12 万 四舍五入
     * @param cent 钱数 分
     * @param unit 是否加单位
     * @return
     */
    public static String formatCent(Integer cent, boolean unit) {
        if (cent == null) {
            return "0" + (unit ? " 元" : "");
        }
        String data;
        BigDecimal decimal = new BigDecimal(cent);
        // 分 --> 元
        decimal = decimal.divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP);
        DecimalFormat decimalFormat = new DecimalFormat("###,##0.00");
        if (decimal.doubleValue() > TEN_THOUSAND) {
            // 元 --> 万 保留两位小数
            data =decimal.divide(BigDecimal.valueOf(10000L), 2, RoundingMode.HALF_UP).doubleValue() + (unit ? " 万" : "");
        } else {
            data = decimalFormat.format(decimal) + (unit ? " 元" : "");
        }
        return data;
    }


    /**
     * 格式化加单位后缀
     * @param cent
     * @return
     */
    public static String formatCentToYuanSuffixUnit(Integer cent) {
        return formatCentToYuan(cent, true, 2, true);
    }

    /**
     * 格式化 没有单位后缀
     * @param cent
     * @return
     */
    public static String formatCentToYuanNotSplitNotSuffixUnit(Integer cent) {
        return formatCentToYuan(cent, false, 2, false);
    }

    /**
     *
     * @param cent 分
     * @param scale 精度
     * @return
     */
    public static String formatCentToYuanNotSplitNotSuffixUnit(Integer cent, int scale) {
        return formatCentToYuan(cent, false, scale, false);
    }

    /**
     * 格式化 没有单位后缀
     * @param cent
     * @return
     */
    public static String formatCentToYuanNotSuffixUnit(Integer cent) {
        return formatCentToYuan(cent, false, 2, true);
    }

    /**
     *
     * @param cent 分
     * @param scale 精度
     * @return
     */
    public static String formatCentToYuanNotSuffixUnit(Integer cent, int scale) {
        return formatCentToYuan(cent, false, scale, true);
    }

    /**
     * 格式化 分 --> 元, 200,999.22 元
     * @param cent 分
     * @param unit 是否加单位
     * @param scale 精度
     * @return
     */
    public static String formatCentToYuan(Integer cent, boolean unit, int scale, boolean split) {
        if (cent == null) {
            return "0" + (unit ? " 元" : "");
        }
        StringBuilder builder = new StringBuilder();
        if (split) {
            builder.append("###,###");
        } else {
            builder.append("#");
        }
        for (int i = 0; i < scale; i++) {
            if (i == 0) {
                builder.append(".0");
            } else {
                builder.append("0");
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat(builder.toString());
        BigDecimal decimal = new BigDecimal(cent);
        // 分 --> 元
        return decimalFormat.format(decimal.divide(BigDecimal.valueOf(100L), scale, RoundingMode.HALF_UP)) + (unit ? " 元" : "");
    }
}

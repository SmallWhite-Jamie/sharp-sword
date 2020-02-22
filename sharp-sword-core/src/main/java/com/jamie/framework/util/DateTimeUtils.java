package com.jamie.framework.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author lizheng
 * @date: 21:27 2020/02/18
 * @Description: DateTimeUtils
 */
public class DateTimeUtils {

    /**
     * 获取当前日日期的字符串形式: 2020-02-18
     * @return
     */
    public static String getDateStr() {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static void main(String[] args) {
        System.out.println(DateTimeUtils.getDateStr());
    }
}

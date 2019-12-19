package com.jamie.framework;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author lizheng
 * @date: 20:26 2019/10/13
 * @Description: DateTest
 */
public class DateTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println(DigestUtil.md5Hex("123456").toUpperCase());
    }
}

package com.sharp.sword.idgenerator;

/**
 * @author lizheng
 * @date: 12:22 2019/10/13
 * @Description: IdGenerator
 */
public interface IdGenerator {
    /**
     * 获得下一个long类型ID
     * @return
     */
    long nextId();

    /**
     * 获得下一个String类型ID
     * @return
     */
    String nextIdStr();

    /**
     * 获取一个long类型ID数组
     * @param size
     * @return
     */
    default long[] nextId(int size) {
        if (size <= 0) {
            return new long[] { nextId() };
        }
        long[] arr = new long[size];
        for (int i = 0; i < size; i++) {
            arr[i] = nextId();
        }
        return arr;
    }

    /**
     * 获取一个字符串类型ID数组
     * @return
     */
    default String[] nextIdStr(int size) {
        if (size <= 0) {
            return new String[] { nextIdStr() };
        }
        String[] arr = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = nextIdStr();
        }
        return arr;
    }

}

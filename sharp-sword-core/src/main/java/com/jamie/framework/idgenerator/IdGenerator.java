package com.jamie.framework.idgenerator;

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
     * 获得一组long类型别id
     * @param nums
     * @return
     */
    long[] nextId(int nums);

    /**
     * 获得一组String类型id
     * @param nums
     * @return
     */
    String[] nextIdStr(int nums);

}

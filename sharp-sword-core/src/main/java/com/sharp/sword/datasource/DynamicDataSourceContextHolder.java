package com.sharp.sword.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizheng
 * @date: 10:16 2019/11/01
 * @Description: DynamicDataSourceContextHolder 保存及获取数据源
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    private static final List<String> DATA_SOURCE_IDS = new ArrayList<>();

    public static final String DEF_KEY = "master";

    public static void setDataSourceType(String dataSourceType) {
        CONTEXT_HOLDER.set(dataSourceType);
    }

    public static String getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }

    public static boolean containsDataSource(String dataSourceId) {
        return DATA_SOURCE_IDS.contains(dataSourceId);
    }

    public static List<String> getDataSourceIds() {
        return DATA_SOURCE_IDS;
    }

    public static void addDataSourceIds(String key) {
        DATA_SOURCE_IDS.add(key);
    }
}

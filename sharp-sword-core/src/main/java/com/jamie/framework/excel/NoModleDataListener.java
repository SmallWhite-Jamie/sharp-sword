package com.jamie.framework.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author lizheng
 * @date: 22:03 2020/02/21
 * @Description: NoModleDataListener
 */
@Slf4j
public class NoModleDataListener extends AnalysisEventListener<Map<Integer, String>> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;

    private List<Map<Integer, String>> data = new ArrayList<>();

    private Consumer<List<Map<Integer, String>>> consumer;

    public NoModleDataListener(Consumer<List<Map<Integer, String>>> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void invoke(Map<Integer, String> integerStringMap, AnalysisContext analysisContext) {
        if (log.isDebugEnabled()) {
            log.debug("解析到一条数据:{}", JSON.toJSONString(data));
        }
        data.add(integerStringMap);
        if (data.size() >= BATCH_COUNT) {
            consumer.accept(data);
            data.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        consumer.accept(data);
        log.info("所有数据解析完成！");
    }
}

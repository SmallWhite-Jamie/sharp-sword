package com.jamie.framework.quartz;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lizheng
 * @date: 12:15 2020/02/15
 * @Description: QuartzCronProperties
 */
@Component
@ConfigurationProperties(prefix = "app.quartz")
public class QuartzCronProperties {

    private Map<String, String> cronMap = new HashMap<>();

    private Map<String, Detail> details = new HashMap<>();

    public Map<String, String> getCronMap() {
        return cronMap;
    }

    public void setCronMap(Map<String, String> cronMap) {
        this.cronMap = cronMap;
    }

    public Map<String, Detail> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Detail> details) {
        this.details = details;
    }

    static class Detail {
        private int interval;
        private int repeatCount;

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getRepeatCount() {
            return repeatCount;
        }

        public void setRepeatCount(int repeatCount) {
            this.repeatCount = repeatCount;
        }
    }

}

package com.jamie.framework.quartz;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private final static Logger LOGGER = LogManager.getLogger(TestService.class);

    public void service1() {
        LOGGER.info("test service1");
    }

}
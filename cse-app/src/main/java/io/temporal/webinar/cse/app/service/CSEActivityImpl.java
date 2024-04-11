package io.temporal.webinar.cse.app.service;

import io.temporal.spring.boot.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@ActivityImpl(taskQueues = CSEActivityImpl.CSE_QUEUE)
public class CSEActivityImpl implements CSEActivity {
    private static final Logger logger = LoggerFactory.getLogger(CSEActivityImpl.class);
    public static final String CSE_QUEUE = "cseQueue";

    @Override
    public String processCSE(String input) {
        long startTime = System.currentTimeMillis();
        logger.info("CSEActivity: input {}", input);
        try {
            logger.info("Thread.sleep...");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String cseResult = input + "; processed by CSE";
        logger.info("CSEActivity result: {}, processing time {} ms", cseResult, System.currentTimeMillis() - startTime);
        return cseResult;
    }
}

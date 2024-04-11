package io.temporal.webinar.es.app.service;

import io.temporal.spring.boot.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@ActivityImpl(taskQueues = ESActivityImpl.ES_QUEUE)
public class ESActivityImpl implements ESActivity {
    private static final Logger logger = LoggerFactory.getLogger(ESActivityImpl.class);
    public static final String ES_QUEUE = "esQueue";

    @Override
    public String processES(String input) {
        long startTime = System.currentTimeMillis();
        logger.info("ESActivity: input {}", input);

        String esResult = input + "; processed by ES";
        logger.info("ESActivity result: {}, processing time {} ms", esResult, System.currentTimeMillis() - startTime);
        return esResult;
    }
}

package io.temporal.webinar.nce.app.service;

import io.temporal.spring.boot.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@ActivityImpl(taskQueues = NCEActivityImpl.NCE_QUEUE)
public class NCEActivityImpl implements NCEActivity {
    private static final Logger log = LoggerFactory.getLogger(NCEActivityImpl.class);
    public static final String NCE_QUEUE = "nceQueue";


    @Override
    public String processNCE(String input) {
        long startTime = System.currentTimeMillis();
        log.info("NCEActivity: input {}", input);

        try {
            log.info("Thread.sleep...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        String nceResult = input + "; processed by NCE";
        log.info("NCEActivity result: {}, processing time {} ms", nceResult, System.currentTimeMillis() - startTime);
        return nceResult;
    }
}

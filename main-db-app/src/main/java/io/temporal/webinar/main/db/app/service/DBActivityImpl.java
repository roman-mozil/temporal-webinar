package io.temporal.webinar.main.db.app.service;

import io.temporal.spring.boot.ActivityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static io.temporal.webinar.main.db.app.service.WorkflowService.DB_QUEUE;


@Component
@ActivityImpl(taskQueues = DB_QUEUE)
public class DBActivityImpl implements DBActivity {
    private static final Logger log = LoggerFactory.getLogger(DBActivityImpl.class);
    private volatile boolean delayFlag = true;

    @Override
    public String processDB(String rsf) {
        long startTime = System.currentTimeMillis();
        log.info("DBActivity: input {}", rsf);


        String dbResult = rsf + " processed by DB";

        log.info("DBActivity result: {}, processing time {} ms", dbResult, System.currentTimeMillis() - startTime);

        return dbResult;
    }

}

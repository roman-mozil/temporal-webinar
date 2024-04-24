package io.temporal.webinar.main.db.app.service;


import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static io.temporal.webinar.main.db.app.service.WorkflowService.DB_QUEUE;

@WorkflowImpl(taskQueues = DB_QUEUE)
public class WorkflowServiceImpl implements WorkflowService {

    private static Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

    //Local Activity
    private DBActivity dbActivity =
            Workflow.newActivityStub(
                    DBActivity.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(20))
                            .setScheduleToCloseTimeout(Duration.ofSeconds(40))
                            .build());
    //remote Activity
    private ESActivity esActivity =
            Workflow.newActivityStub(
                    ESActivity.class,
                    ActivityOptions.newBuilder()
                            .setTaskQueue(ES_QUEUE)
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .setScheduleToCloseTimeout(Duration.ofSeconds(15))
                            .setRetryOptions(RetryOptions.newBuilder()
                                            .setInitialInterval(Duration.ofSeconds(1))
                                            .setMaximumInterval(Duration.ofSeconds(10))
                                            .build())
                            .build());
    //remote Activity
    private CSEActivity cseActivity =
            Workflow.newActivityStub(
                    CSEActivity.class,
                    ActivityOptions.newBuilder()
                            .setTaskQueue(CSE_QUEUE)
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .setScheduleToCloseTimeout(Duration.ofSeconds(15))
                            .build());
    //remote Activity
    private NCEActivity nceActivity =
            Workflow.newActivityStub(
                    NCEActivity.class,
                    ActivityOptions.newBuilder()
                            .setTaskQueue(NCE_QUEUE)
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .setScheduleToCloseTimeout(Duration.ofSeconds(15))
                            .build());

    @Override
    public String startWorkflow(String input) {
        long startTime = System.currentTimeMillis();
        logger.info("Workflow started with input = {}", input);

        long dbSendTime = System.currentTimeMillis();
        logger.info("Send to DBActivity at {}", dbSendTime);
        String dbActivityResult = dbActivity.processDB(input);
        logger.info("Received DBActivity result in {} ms", System.currentTimeMillis()-dbSendTime);

        long esSendTime = System.currentTimeMillis();
        Promise<String> esActivityPromise =
                Async.function(
                        () -> {
                            String esResult = esActivity.processES(input);
                            logger.info("Received ESActivity result in {} ms", System.currentTimeMillis()-esSendTime);
                            return esResult;
                        });

        long cseSendTime = System.currentTimeMillis();
        Promise<String> cseActivityPromise =
                Async.function(
                        () -> {
                            String cseResult = cseActivity.processCSE(input);
                            logger.info("Received CSEActivity result in {} ms", System.currentTimeMillis()-cseSendTime);
                            return cseResult;
                        });

        long nceSendTime = System.currentTimeMillis();
        Promise<String> nceActivityPromise =
                Async.function(
                        () -> {
                            String nceResult = nceActivity.processNCE(input);
                            logger.info("Received NCEActivity result in {} ms", System.currentTimeMillis()-nceSendTime);
                            return nceResult;
                        });

        Promise<Void> voidPromise = Promise.allOf(esActivityPromise, cseActivityPromise, nceActivityPromise);//executes Promises in parallel
        String esActivityResult = esActivityPromise.get();
        String cseActivityResult = cseActivityPromise.get();
        String nceActivityResult = nceActivityPromise.get();

        String finalResult = String.join(",", dbActivityResult, esActivityResult, cseActivityResult, nceActivityResult);

        logger.info("Workflow result = {}, processing time {} ms", finalResult, System.currentTimeMillis()-startTime);
        return finalResult;
    }
}

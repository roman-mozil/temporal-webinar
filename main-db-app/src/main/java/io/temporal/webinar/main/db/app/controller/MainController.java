package io.temporal.webinar.main.db.app.controller;

import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.webinar.main.db.app.entity.InputRequest;
import io.temporal.webinar.main.db.app.service.WorkflowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.temporal.webinar.main.db.app.service.WorkflowService.DB_QUEUE;


@RestController
@RequestMapping("/api")
public class MainController {

    private WorkflowClient client;
    private static Logger logger = LoggerFactory.getLogger(MainController.class);


    @Autowired
    public MainController( WorkflowClient client) {
        this.client = client;
    }

    @PostMapping("/startWorkflow")
    public ResponseEntity handleRequest(@RequestBody InputRequest inputRequest) {
        logger.info("Controller, input = {}", inputRequest);

        WorkflowService workflow =
                client.newWorkflowStub(
                        WorkflowService.class,
                        WorkflowOptions.newBuilder()
                                .setTaskQueue(DB_QUEUE)
                                .build());

        String workflowResult = workflow.startWorkflow(inputRequest.getInput());

        return new ResponseEntity<>(workflowResult, HttpStatus.OK);
    }

}

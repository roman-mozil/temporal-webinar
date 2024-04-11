package io.temporal.webinar.main.db.app.service;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface WorkflowService {
    String DB_QUEUE = "dbQueue";
    String CSE_QUEUE = "cseQueue";
    String ES_QUEUE = "esQueue";
    String NCE_QUEUE = "nceQueue";


    @WorkflowMethod
    String startWorkflow(String input);

}

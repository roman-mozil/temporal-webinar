package io.temporal.webinar.main.db.app.service;


import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface ESActivity {

    @ActivityMethod
    String processES(String input);

}

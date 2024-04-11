package io.temporal.webinar.cse.app.service;


import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface CSEActivity {

    @ActivityMethod
    String processCSE(String input);

}

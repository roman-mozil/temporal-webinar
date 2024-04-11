package io.temporal.webinar.main.db.app.service;


import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface NCEActivity {

    @ActivityMethod
    String processNCE(String input);

}

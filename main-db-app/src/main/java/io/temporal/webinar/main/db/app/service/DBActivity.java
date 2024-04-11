package io.temporal.webinar.main.db.app.service;


import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface DBActivity {

    String processDB(String input);

}

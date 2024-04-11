package io.temporal.webinar.main.db.app.entity;

public class InputRequest {
    private String input;

    public InputRequest(){}
    public InputRequest(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

}

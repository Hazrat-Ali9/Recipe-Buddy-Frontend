package com.project.recipiebuddy.model;

public class RecipieSteps {
    String step_text;
    int time_sec;

    public String getStep_text() {
        return step_text;
    }

    public void setStep_text(String step_text) {
        this.step_text = step_text;
    }

    public int getTime_sec() {
        return time_sec;
    }

    public void setTime_sec(int time_sec) {
        this.time_sec = time_sec;
    }

    public RecipieSteps(String step_text, int time_sec) {
        this.step_text = step_text;
        this.time_sec = time_sec;
    }
}

package com.project.recipiebuddy.model;

import java.util.ArrayList;

public class Recipie {
    String ingridients;
    ArrayList<RecipieSteps> recipieSteps;

    public Recipie(String ingridients, ArrayList<RecipieSteps> recipieSteps) {
        this.ingridients = ingridients;
        this.recipieSteps = recipieSteps;
    }

    public String getIngridients() {
        return ingridients;
    }

    public void setIngridients(String ingridients) {
        this.ingridients = ingridients;
    }

    public ArrayList<RecipieSteps> getRecipieSteps() {
        return recipieSteps;
    }

    public void setRecipieSteps(ArrayList<RecipieSteps> recipieSteps) {
        this.recipieSteps = recipieSteps;
    }
}

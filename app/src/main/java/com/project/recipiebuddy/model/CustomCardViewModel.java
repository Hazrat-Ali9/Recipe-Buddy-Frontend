package com.project.recipiebuddy.model;

public class CustomCardViewModel {
    String name;
    int personImg;

    String rId;

    public CustomCardViewModel(String name, int personImg, String rId) {
        this.name = name;
        this.rId=rId;
        this.personImg = personImg;
    }

    public String getrId() {
        return rId;
    }

    public void setrId(String rId) {
        this.rId = rId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonImg() {
        return personImg;
    }

    public void setPersonImg(int personImg) {
        this.personImg = personImg;
    }
}

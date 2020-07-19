package com.exemple.enjoyfood.model;

public class ScreenItem {

    String title, description;
    int screenImg, son;

    public ScreenItem(String title, String description, int screenImg, int son) {
        this.title = title;
        this.description = description;
        this.screenImg = screenImg;
        this.son = son;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getScreenImg() {
        return screenImg;
    }

    public int getSon() {
        return son;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScreenImg(int screenImg) {
        this.screenImg = screenImg;
    }

    public void setSon(int son) {
        this.son = son;
    }
}

package com.exemple.enjoyfood.model;

public class Categorie {
    private String label;
    private int logo;

    public Categorie(String label, int logo) {
        this.label = label;
        this.logo = logo;
    }

    public String getLabel() {
        return label;
    }

    public int getLogo() {
        return logo;
    }
}

package com.exemple.enjoyfood.model;

public class Produit {
    private String code_bar;
    private String titre;
    private String description;
    private String image;
    private String categorie;
    private double energie;
    private double matiere_grasse;
    private double graisse;
    private double glucide;
    private double sucre;
    private double proteine;
    private double fibre;
    private double sodium;
    private double sel;
    private double calicium;
    private int fruits_legumes;
    private String ingrediant;
    private int volume;

    public Produit(String image, String titre, String description, String code_bar) {
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.code_bar = code_bar;
    }

    public Produit(String code_bar, String titre, String description, String image, String categorie, double energie, double matiere_grasse, double graisse, double glucide, double sucre, double proteine, double fibre, double sodium, double sel, double calicium, int fruits_legumes, String ingrediant, int volume) {
        this.code_bar = code_bar;
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.categorie = categorie;
        this.energie = energie;
        this.matiere_grasse = matiere_grasse;
        this.graisse = graisse;
        this.glucide = glucide;
        this.sucre = sucre;
        this.proteine = proteine;
        this.fibre = fibre;
        this.sodium = sodium;
        this.sel = sel;
        this.calicium = calicium;
        this.fruits_legumes = fruits_legumes;
        this.ingrediant = ingrediant;
        this.volume = volume;
    }

    public String getCategorie() {
        return categorie;
    }

    public double getEnergie() {
        return energie;
    }

    public double getMatiere_grasse() {
        return matiere_grasse;
    }

    public double getGraisse() {
        return graisse;
    }

    public double getGlucide() {
        return glucide;
    }

    public double getSucre() {
        return sucre;
    }

    public double getProteine() {
        return proteine;
    }

    public double getFibre() {
        return fibre;
    }

    public double getSodium() {
        return sodium;
    }

    public double getSel() {
        return sel;
    }

    public double getCalicium() {
        return calicium;
    }

    public int getFruits_legumes() {
        return fruits_legumes;
    }

    public String getIngrediant() {
        return ingrediant;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setEnergie(double energie) {
        this.energie = energie;
    }

    public void setMatiere_grasse(double matiere_grasse) {
        this.matiere_grasse = matiere_grasse;
    }

    public void setGraisse(double graisse) {
        this.graisse = graisse;
    }

    public void setGlucide(double glucide) {
        this.glucide = glucide;
    }

    public void setSucre(double sucre) {
        this.sucre = sucre;
    }

    public void setProteine(double proteine) {
        this.proteine = proteine;
    }

    public void setFibre(double fibre) {
        this.fibre = fibre;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public void setSel(double sel) {
        this.sel = sel;
    }

    public void setCalicium(double calicium) {
        this.calicium = calicium;
    }

    public void setFruits_legumes(int fruits_legumes) {
        this.fruits_legumes = fruits_legumes;
    }

    public void setIngrediant(String ingrediant) {
        this.ingrediant = ingrediant;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCode_bar() {
        return code_bar;
    }

    public void setCode_bar(String code_bar) {
        this.code_bar = code_bar;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
}

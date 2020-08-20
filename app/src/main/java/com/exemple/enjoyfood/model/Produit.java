package com.exemple.enjoyfood.model;

public class Produit {
    private String code_bar;
    private String titre;
    private String titre_en;
    private String titre_ar;
    private String description;
    private String desc_en;
    private String desc_ar;
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
    private String ingrediant_en;
    private String ingrediant_ar;
    private int volume;

    public Produit(String image, String titre, String description, String code_bar) {
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.code_bar = code_bar;
    }

    public Produit(String code_bar, String titre, String titre_en, String titre_ar, String description, String desc_en, String desc_ar, String image, String categorie, double energie, double matiere_grasse, double graisse, double glucide, double sucre, double proteine, double fibre, double sodium, double sel, double calicium, int fruits_legumes, String ingrediant, String ingrediant_en, String ingrediant_ar, int volume) {
        this.code_bar = code_bar;
        this.titre = titre;
        this.titre_en = titre_en;
        this.titre_ar = titre_ar;
        this.description = description;
        this.desc_en = desc_en;
        this.desc_ar = desc_ar;
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
        this.ingrediant_en = ingrediant_en;
        this.ingrediant_ar = ingrediant_ar;
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

    public String getDesc_ar() {
        return desc_ar;
    }

    public void setDesc_ar(String desc_ar) {
        this.desc_ar = desc_ar;
    }

    public String getTitre_en() {
        return titre_en;
    }

    public void setTitre_en(String titre_en) {
        this.titre_en = titre_en;
    }

    public String getTitre_ar() {
        return titre_ar;
    }

    public void setTitre_ar(String titre_ar) {
        this.titre_ar = titre_ar;
    }

    public String getDesc_en() {
        return desc_en;
    }

    public void setDesc_en(String desc_en) {
        this.desc_en = desc_en;
    }

    public String getIngrediant_en() {
        return ingrediant_en;
    }

    public void setIngrediant_en(String ingrediant_en) {
        this.ingrediant_en = ingrediant_en;
    }

    public String getIngrediant_ar() {
        return ingrediant_ar;
    }

    public void setIngrediant_ar(String ingrediant_ar) {
        this.ingrediant_ar = ingrediant_ar;
    }
}

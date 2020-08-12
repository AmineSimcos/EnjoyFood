package com.exemple.enjoyfood;

public class Config {
    public static final String ADRESSE_IP = "192.168.43.89";
    public static final String ROOT_URL = "http://" + ADRESSE_IP + "/enjoyfood/";
    public static final String URL_PRODUCT = ROOT_URL + "getProduct.php";
    public static final String URL_ALL_PRODUCT = ROOT_URL + "getAll.php";
    public static final String URL_PHOTO = ROOT_URL + "image/product/";
    public static final String URL_REGISTER = ROOT_URL + "register.php";
    public static final String URL_LOGIN = ROOT_URL + "login.php";
    public static final String URL_CONSOMMATION = ROOT_URL + "consommation.php";
    public static final String URL_ADD_CONSOMMATION = ROOT_URL + "ajouter_consommation.php";
    public static final String URL_ADD_HISTORY = ROOT_URL + "ajouter_historique.php";
    public static final String URL_HISTORY = ROOT_URL + "history.php";
    public static final String URL_DELL_HISTORY = ROOT_URL + "supp_historique.php";

    public static final int SPALSH_SCREEN_TIMEOUT = 1500;
    public static final boolean ANIMATION_BUTTON_ACTIVE = true;

    public static final String CATEGORIES[] = { "Boissons", "Produits laitiers", "Matières grasses", "Produits sucrés", "Céréales et féculents", "Fruits et Legumes", "Eau","Autres"};


}

package com.exemple.enjoyfood;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dz.baichoudjedi.lovefood.model.Categorie;

public class ExpandableListDataPump {
    public static HashMap<String, List<Categorie>> getData() {


        HashMap<String, List<Categorie>> expandableListDetail = new HashMap<String, List<Categorie>>();

        List<Categorie> produitsLetiers = new ArrayList();
        produitsLetiers.add(new Categorie("Beurres & margarins", R.drawable.beurres));
        produitsLetiers.add(new Categorie("Cremes fraiches et chantilly", R.drawable.chtt));
        produitsLetiers.add(new Categorie("Lait, boissons lactées", R.drawable.lait));
        produitsLetiers.add(new Categorie("Yaourt cremes desserts", R.drawable.yaourt_cremes_desserts));
        produitsLetiers.add(new Categorie("Glaces", R.drawable.glaces));

        List<Categorie> epicerie = new ArrayList();


        List<Categorie> pates = new ArrayList();


        List<Categorie> epicerieSucre = new ArrayList();
        epicerieSucre.add(new Categorie("Céréales", R.drawable.cereales));
        epicerieSucre.add(new Categorie("Biscuits et gateaux", R.drawable.biscuit));
        epicerieSucre.add(new Categorie("Pates à tartines", R.drawable.pates));


        List<Categorie> boissons = new ArrayList();
        boissons.add(new Categorie("Jus", R.drawable.jus));
        boissons.add(new Categorie("Eau", R.drawable.eau));
        boissons.add(new Categorie("Gaz", R.drawable.gaz));

        expandableListDetail.put("Produits létiers", produitsLetiers);
        expandableListDetail.put("Epicerie sucré", epicerieSucre);
        expandableListDetail.put("Boissons", boissons);
        expandableListDetail.put("Epicerie salé", epicerie);
        expandableListDetail.put("Pates, Riz, Féculents", pates);
        return expandableListDetail;
    }
}

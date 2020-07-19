package com.exemple.enjoyfood.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.exemple.enjoyfood.DownLoadImageTask;
import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.URLs;
import com.exemple.enjoyfood.model.Produit;


public class Tab1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txtTitre, txtDescription, txtValeur_nutri_1, txtValeur_nutri_2;
    private TextView txtEnergie, txtEnergieKJ, txtEnergieKJ250, txtMatiereGrasse, txtGrasseS, txtGlucide, txtSucre, txtProteine, txtFibre, txtSodium, txtSel, txtCalicium, txtFruitsLesgumes, txtIngrediant;
    private TextView txtEnergie250, txtMatiereGrasse250, txtGrasseS250, txtGlucide250, txtSucre250, txtProteine250, txtFibre250, txtSodium250, txtSel250, txtCalicium250, txtFruitsLesgumes250;
    private LinearLayout ligneEnergie1, ligneEnergie2, ligneMatiereGrasse, ligneGrasseS, ligneGlucide, ligneSucre, ligneProteine, ligneFibre, ligneSodium, ligneSel, ligneCalicium, ligneFruitsLesgumes;
    private View separateurEnergie, separateurMatiereGrasse, separateurGrasseS, separateurGlucide, separateurSucre, separateurProteine, separateurFibre, separateurSodium, separateurSel, separateurCalicium, separateurFruitsLesgumes;


    private String mParam1;
    private String mParam2;

    private PreferenceFragment.OnPreferenceStartFragmentCallback mListener;
    public Tab1() {
        // Required empty public constructor
    }


    public static Tab1 newInstance(String param1, String param2) {
        Tab1 fragment = new Tab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);
        txtValeur_nutri_1 = v.findViewById(R.id.valeur_nutri_1);
        txtValeur_nutri_2 = v.findViewById(R.id.valeur_nutri_2);

        txtEnergie = v.findViewById(R.id.txtEnergie);
        txtEnergieKJ = v.findViewById(R.id.txtEnergie_kjoul);
        txtMatiereGrasse = v.findViewById(R.id.txtMatiereGrasse);
        txtGrasseS = v.findViewById(R.id.txtGraisseS);
        txtGlucide = v.findViewById(R.id.txtGlucide);
        txtSucre = v.findViewById(R.id.txtsucre);
        txtProteine = v.findViewById(R.id.txtProteine);
        txtFibre = v.findViewById(R.id.txtFibre);
        txtSodium = v.findViewById(R.id.txtSodium);
        txtSel = v.findViewById(R.id.txtSel);
        txtCalicium = v.findViewById(R.id.txtCalicium);
        txtFruitsLesgumes = v.findViewById(R.id.txtFruitsLegumes);

        txtEnergie250 = v.findViewById(R.id.txtEnergie250);
        txtEnergieKJ250 = v.findViewById(R.id.txtEnergie250kjoul);
        txtMatiereGrasse250 = v.findViewById(R.id.txtMatiereGrasse250);
        txtGrasseS250 = v.findViewById(R.id.txtGraisseS250);
        txtGlucide250 = v.findViewById(R.id.txtGlucide250);
        txtSucre250 = v.findViewById(R.id.txtsucre250);
        txtProteine250 = v.findViewById(R.id.txtProteine250);
        txtFibre250 = v.findViewById(R.id.txtfibre250);
        txtSodium250 = v.findViewById(R.id.txtSodium250);
        txtSel250 = v.findViewById(R.id.txtSel250);
        txtCalicium250 = v.findViewById(R.id.txtCalicium250);
        txtFruitsLesgumes250 = v.findViewById(R.id.txtFruitsLegumes250);

        txtIngrediant = v.findViewById(R.id.txtIngrediant);

        ligneEnergie1 = v.findViewById(R.id.ligne_energie_1);
        ligneEnergie2 = v.findViewById(R.id.ligne_energie_2);
        ligneMatiereGrasse = v.findViewById(R.id.ligne_matiere_grasse);
        ligneGrasseS = v.findViewById(R.id.ligne_graisse_saturee);
        ligneGlucide = v.findViewById(R.id.ligne_glucide);
        ligneSucre = v.findViewById(R.id.ligne_sucres);
        ligneProteine = v.findViewById(R.id.ligne_proteine);
        ligneFibre = v.findViewById(R.id.ligne_fibre);
        ligneSodium = v.findViewById(R.id.ligne_sodium);
        ligneSel = v.findViewById(R.id.ligne_sel);
        ligneCalicium = v.findViewById(R.id.ligne_calicium);
        ligneFruitsLesgumes = v.findViewById(R.id.ligne_fruits_lesgumes);

        separateurEnergie = v.findViewById(R.id.separateur_energie);
        separateurMatiereGrasse = v.findViewById(R.id.separateur_matiere_grasse);
        separateurGrasseS = v.findViewById(R.id.separateur_graisse_saturee);
        separateurGlucide = v.findViewById(R.id.separateur_glucide);
        separateurSucre = v.findViewById(R.id.separateur_sucres);
        separateurProteine = v.findViewById(R.id.separateur_proteine);
        separateurFibre = v.findViewById(R.id.separateur_fibre);
        separateurSodium = v.findViewById(R.id.separateur_sodium);
        separateurSel = v.findViewById(R.id.separateur_sel);
        separateurCalicium = v.findViewById(R.id.separateur_calicium);
        separateurFruitsLesgumes = v.findViewById(R.id.separateur_fruits_lesgumes);

        ArrayList<String> listeBoissons, listeGrassse;
        listeBoissons = new ArrayList<>();
        listeGrassse = new ArrayList<>();

        listeBoissons.add("Jus");
        listeBoissons.add("Gaz");

        listeGrassse.add("Beurres & margarins");
        listeGrassse.add("pates à tartines");


        if(ResultatActivity.produit.getEnergie() == 0){
            ligneEnergie1.setVisibility(View.GONE);
            ligneEnergie2.setVisibility(View.GONE);
            separateurEnergie.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getMatiere_grasse() == 0){
            ligneMatiereGrasse.setVisibility(View.GONE);
            separateurMatiereGrasse.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getGraisse() == 0){
            ligneGrasseS.setVisibility(View.GONE);
            separateurGrasseS.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getGlucide() == 0){
            ligneGlucide.setVisibility(View.GONE);
            separateurGlucide.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getSucre() == 0){
            ligneSucre.setVisibility(View.GONE);
            separateurSucre.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getProteine() == 0){
            ligneProteine.setVisibility(View.GONE);
            separateurProteine.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getSodium() == 0){
            ligneSodium.setVisibility(View.GONE);
            separateurSodium.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getSel() == 0){
            ligneSel.setVisibility(View.GONE);
            separateurSel.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getFibre() == 0){
            ligneFibre.setVisibility(View.GONE);
            separateurFibre.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getCalicium() == 0){
            ligneCalicium.setVisibility(View.GONE);
            separateurCalicium.setVisibility(View.GONE);
        }
        if(ResultatActivity.produit.getFruits_legumes() == 0){
            ligneFruitsLesgumes.setVisibility(View.GONE);
            separateurFruitsLesgumes.setVisibility(View.GONE);
        }
        ///////////////////////////////////////////////////////////////////////////




        /////////////////////////// Partie 2 //////////////////////////////////////

        if(listeBoissons.contains(ResultatActivity.produit.getCategorie())){
            txtValeur_nutri_1.setText(R.string.for100ml);
            txtValeur_nutri_2.setText(R.string.for250ml);
        }else{
            txtValeur_nutri_1.setText(R.string.for100g);
            txtValeur_nutri_2.setText(R.string.for250g);
        }
        txtEnergie.setText(String.valueOf(ResultatActivity.produit.getEnergie()) + " " + getResources().getString(R.string.unity_kcal));
        txtEnergieKJ.setText((ResultatActivity.produit.getEnergie() * 4.184) + " " + getResources().getString(R.string.unity_kj));
        txtMatiereGrasse.setText(ResultatActivity.produit.getMatiere_grasse() + " " + getResources().getString(R.string.unity_g));
        txtGrasseS.setText(ResultatActivity.produit.getGraisse() + " " + getResources().getString(R.string.unity_g));
        txtGlucide.setText(ResultatActivity.produit.getGlucide() + " " + getResources().getString(R.string.unity_g));
        txtSucre.setText(ResultatActivity.produit.getSucre() + " " + getResources().getString(R.string.unity_g));
        txtProteine.setText(ResultatActivity.produit.getProteine() + " " + getResources().getString(R.string.unity_g));
        txtSodium.setText(ResultatActivity.produit.getSodium() + " " + getResources().getString(R.string.unity_g));
        txtSel.setText(ResultatActivity.produit.getSel() + " " + getResources().getString(R.string.unity_g));
        txtFibre.setText(ResultatActivity.produit.getFibre() + " " + getResources().getString(R.string.unity_g));
        txtCalicium.setText(ResultatActivity.produit.getCalicium() + " " + getResources().getString(R.string.unity_g));
        txtFruitsLesgumes.setText(ResultatActivity.produit.getFruits_legumes() + " %");

        txtEnergie250.setText(ResultatActivity.produit.getEnergie()*250/100 + " " + getResources().getString(R.string.unity_kcal));
        txtEnergieKJ250.setText((ResultatActivity.produit.getEnergie() * 4.184)*250/100 + " " + getResources().getString(R.string.unity_kj));
        txtMatiereGrasse250.setText(ResultatActivity.produit.getMatiere_grasse()*250/100 + " " + getResources().getString(R.string.unity_g));
        txtGrasseS250.setText(ResultatActivity.produit.getGraisse()*250/100 + " " + getResources().getString(R.string.unity_g));
        txtGlucide250.setText(ResultatActivity.produit.getGlucide()*250/100 + " " + getResources().getString(R.string.unity_g));
        txtSucre250.setText(ResultatActivity.produit.getSucre()*250/100 + " " + getResources().getString(R.string.unity_g));
        txtProteine250.setText(ResultatActivity.produit.getProteine()*250/100 + " " + getResources().getString(R.string.unity_g));
        txtSodium250.setText(ResultatActivity.produit.getSodium()*250/100 + " " + getResources().getString(R.string.unity_g));
        txtSel250.setText(ResultatActivity.produit.getSel()*250/100 + " " + getResources().getString(R.string.unity_g));
        txtFibre250.setText(ResultatActivity.produit.getFibre()*250/100 + " " + getResources().getString(R.string.unity_g));
        txtCalicium250.setText(ResultatActivity.produit.getCalicium()*250/100 + " " + getResources().getString(R.string.unity_g));

        return v;
    }
}

package com.exemple.enjoyfood.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.model.Produit;

import java.util.Locale;

public class Tab2 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView txtIngrediant;


    private String mParam1;
    private String mParam2;

    public Tab2() {

    }

    public static Tab2 newInstance(String param1, String param2) {
        Tab2 fragment = new Tab2();
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
        View v =  inflater.inflate(R.layout.fragment_tab2, container, false);

        txtIngrediant = v.findViewById(R.id.txtIngrediant);
//        Bundle b = getActivity().getIntent().getExtras();
//        final String code_Bar = b.getString("code_bar");
//        final String titre = b.getString("titre");
//        final String description = b.getString("description");
//        final String image = b.getString("image");
//        final String categorie = b.getString("categorie");
//        final double energie = b.getDouble("energie");
//        final double matiere_grasse = b.getDouble("matiere_grasse");
//        final double graisse = b.getDouble("graisse");
//        final double glucide = b.getDouble("glucide");
//        final double sucre = b.getDouble("sucre");
//        final double proteine = b.getDouble("proteine");
//        final double fibre = b.getDouble("fibres");
//        final double sodium = b.getDouble("sodium");
//        final double sel = b.getDouble("sel");
//        final double calicium = b.getDouble("calicium");
//        final int fruits_lesgumes = b.getInt("fruits_lesgumes");
//        final String ingrediant = b.getString("ingrediant");
//
//        Produit produit = new Produit(code_Bar, titre, description, image, categorie, energie, matiere_grasse, graisse, glucide, sucre, proteine, fibre, sodium, sel, calicium, fruits_lesgumes, ingrediant);

        if(Locale.getDefault().getLanguage().equals("fr")) {
            txtIngrediant.setText(ResultatActivity.produit.getIngrediant());
        }
        else if(Locale.getDefault().getLanguage().equals("en")) {
            txtIngrediant.setText(ResultatActivity.produit.getIngrediant_en());
        }
        else if(Locale.getDefault().getLanguage().equals("ar")) {
            txtIngrediant.setText(ResultatActivity.produit.getIngrediant_ar());
        }

        return v;
    }
}

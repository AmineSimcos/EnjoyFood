package com.exemple.enjoyfood.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.android.material.tabs.TabLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import com.exemple.enjoyfood.DownLoadImageTask;
import com.exemple.enjoyfood.SessionManager;
import com.exemple.enjoyfood.VolleySingleton;
import com.exemple.enjoyfood.model.Produit;
import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.Config;
import com.exemple.enjoyfood.myrequest.MyRequest;
import com.exemple.enjoyfood.myadapter.PagerAdapter;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;


public class ResultatActivity extends AppCompatActivity implements Dialog.DialogListener {

    public static int point = 0, point_N = 0, point_P = 0, fruits_legumesScore = 0, fibreScore = 0;
    private ArrayList<String> listeBoissons, listeGrassse;
    public static Produit produit;
    private ImageView iv;
    private ProgressBar pg;
    private TextView A, B, C, D, E;
    private TextView txtTitre, txtDescription;
    private CircleImageView btn_add;
    private RequestQueue queue;
    private MyRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_add = findViewById(R.id._btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        // Activer l'animation du boutton
        if(Config.ANIMATION_BUTTON_ACTIVE) {
            PushDownAnim.setPushDownAnimTo(btn_add)
                    .setScale(MODE_SCALE, 0.89f)
                    .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                    .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                    .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                    .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        }

        iv = findViewById(R.id.iv);
        pg = findViewById(R.id.pg_image_produit_resultat);

        TabLayout t = findViewById(R.id.tabLayout);
        t.addTab(t.newTab().setText("Tableau calorique"));
        t.addTab(t.newTab().setText("Ingrédients"));
        t.addTab(t.newTab().setText("Statistique"));
        t.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = findViewById(R.id.viewPager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),t.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(t));
        t.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ////////////////////////Partie initialisation //////////////////////////////////////////////
        txtTitre = findViewById(R.id.titre);
        txtDescription = findViewById(R.id.description);

        A = findViewById(R.id.A);
        B = findViewById(R.id.B);
        C = findViewById(R.id.C);
        D = findViewById(R.id.D);
        E = findViewById(R.id.E);
        ////////////////////////////////////////////////////////////////////////////////////////////
        Bundle b = getIntent().getExtras();
        final String code_Bar = b.getString("code_bar");
        final String titre = b.getString("titre");
        final String description = b.getString("description");
        final String image = b.getString("image");
        final String categorie = b.getString("categorie");
        final double energie = b.getDouble("energie");
        final double matiere_grasse = b.getDouble("matiere_grasse");
        final double graisse = b.getDouble("graisse");
        final double glucide = b.getDouble("glucide");
        final double sucre = b.getDouble("sucre");
        final double proteine = b.getDouble("proteine");
        final double fibre = b.getDouble("fibres");
        final double sodium = b.getDouble("sodium");
        final double sel = b.getDouble("sel");
        final double calicium = b.getDouble("calicium");
        final int fruits_lesgumes = b.getInt("fruits_lesgumes");
        final String ingrediant = b.getString("ingrediant");

        produit = new Produit (code_Bar, titre, description, image, categorie, energie, matiere_grasse, graisse, glucide, sucre, proteine, fibre, sodium, sel, calicium, fruits_lesgumes, ingrediant);
        getSupportActionBar().setTitle(titre);
        listeBoissons = new ArrayList<>();
        listeGrassse = new ArrayList<>();

        listeBoissons.add("Jus");
        listeBoissons.add("Gaz");

        listeGrassse.add("Viandes, poissons, oeufs");
        listeGrassse.add("Corps gras");

        if(produit.getImage().equals("null")){
            iv.setVisibility(View.VISIBLE);
            pg.setVisibility(View.GONE);
        }
        else{
            new DownLoadImageTask(iv).execute(Config.URL_PHOTO + produit.getImage());
            iv.setVisibility(View.VISIBLE);
            pg.setVisibility(View.GONE);
        }

        ///////////////////////////////////////////////////////////////////////////




        /////////////////////////// Partie 2 //////////////////////////////////////
        txtTitre.setText(produit.getTitre());
        txtDescription.setText(produit.getDescription());


        /////// Vérification Nutri-Score /////////////////////////////////////////////////////////
        ////// calculer les points ///////////////////////////////////////////////////////////////
        ////// Etape 1  ///////////////////////////////////////////////////////////////
        //                          calcules les point en communs
        //***** parraport les boissons ******
        if(produit.getCategorie().equals("Boissons")) {
            // calcul de l'energie
            if (produit.getEnergie() <= 30) point_N += 1;
            else if (produit.getEnergie() <= 60 && produit.getEnergie() > 30) point_N += 2;
            else if (produit.getEnergie() <= 90 && produit.getEnergie() > 60) point_N += 3;
            else if (produit.getEnergie() <= 120 && produit.getEnergie() > 90) point_N += 4;
            else if (produit.getEnergie() <= 150 && produit.getEnergie() > 120) point_N += 5;
            else if (produit.getEnergie() <= 180 && produit.getEnergie() > 150) point_N += 6;
            else if (produit.getEnergie() <= 210 && produit.getEnergie() > 180) point_N += 7;
            else if (produit.getEnergie() <= 240 && produit.getEnergie() > 210) point_N += 8;
            else if (produit.getEnergie() <= 270 && produit.getEnergie() > 240) point_N += 9;
            else if (produit.getEnergie() > 270) point_N += 10;
            // calcul du sucre
            if(produit.getSucre() > 4.5 && produit.getSucre() <= 9) point_N += 1;
            else if (produit.getSucre() > 9 && produit.getSucre() <= 13.5) point_N += 2;
            else if (produit.getSucre() > 13.5 && produit.getSucre() <= 18) point_N += 3;
            else if (produit.getSucre() > 18 && produit.getSucre() <= 22.5) point_N += 4;
            else if (produit.getSucre() > 22.5 && produit.getSucre() <= 27) point_N += 5;
            else if (produit.getSucre() > 27 && produit.getSucre() <= 31) point_N += 6;
            else if (produit.getSucre() > 31 && produit.getSucre() <= 36) point_N += 7;
            else if (produit.getSucre() > 36 && produit.getSucre() <= 40) point_N += 8;
            else if (produit.getSucre() > 40 && produit.getSucre() <= 45) point_N += 9;
            else if (produit.getSucre() > 45) point_N += 10;
            // Graisses saturées
            if(produit.getGraisse() > 1 && produit.getGraisse() <=2) point_N += 1;
            else if (produit.getGraisse() > 2 && produit.getGraisse() <=3) point_N += 2;
            else if (produit.getGraisse() > 3 && produit.getGraisse() <=4) point_N += 3;
            else if (produit.getGraisse() > 4 && produit.getGraisse() <=5) point_N += 4;
            else if (produit.getGraisse() > 5 && produit.getGraisse() <=6) point_N += 5;
            else if (produit.getGraisse() > 6 && produit.getGraisse() <=7) point_N += 6;
            else if (produit.getGraisse() > 7 && produit.getGraisse() <=8) point_N += 7;
            else if (produit.getGraisse() > 8 && produit.getGraisse() <=9) point_N += 8;
            else if (produit.getGraisse() > 9 && produit.getGraisse() <=10) point_N += 9;
            else if (produit.getGraisse() > 10) point_N += 10;
            // Sodium
            if(produit.getSodium() > 90 && produit.getSodium() <= 180) point_N += 1;
            else if (produit.getSodium()*1000 > 180 && produit.getSodium()*1000 <= 270) point_N += 2;
            else if (produit.getSodium()*1000 > 270 && produit.getSodium()*1000 <= 360) point_N += 3;
            else if (produit.getSodium()*1000 > 360 && produit.getSodium()*1000 <= 450) point_N += 4;
            else if (produit.getSodium()*1000 > 450 && produit.getSodium()*1000 <= 540) point_N += 5;
            else if (produit.getSodium()*1000 > 540 && produit.getSodium()*1000 <= 360) point_N += 6;
            else if (produit.getSodium()*1000 > 630 && produit.getSodium()*1000 <= 720) point_N += 7;
            else if (produit.getSodium()*1000 > 720 && produit.getSodium()*1000 <= 810) point_N += 8;
            else if (produit.getSodium()*1000 > 810 && produit.getSodium()*1000 <= 900) point_N += 9;
            else if (produit.getSodium()*1000 > 900) point_N += 10;
            // calcul du fruits et lesgumes
            if(produit.getFruits_legumes() > 40 && produit.getFruits_legumes() <= 60){
                point_N += 2;
                fruits_legumesScore += 2;
            }
            else if (produit.getFruits_legumes() > 60 && produit.getFruits_legumes() <= 80){
                point_N += 4;
                fruits_legumesScore += 4;
            }
            else if (produit.getFruits_legumes() > 80){
                point_N += 10;
                fruits_legumesScore += 10;
            }
            //Fibres
            if(produit.getFibre() > 0.7) {
                point_N += 1;
                fibreScore += 1;
            }
            else if (produit.getFibre() > 1.4 && produit.getFibre() <= 2.1){
                point_N += 2;
                fibreScore += 2;
            }
            else if (produit.getFibre() > 2.1 && produit.getFibre() <= 2.8){
                point_N += 3;
                fibreScore += 3;
            }
            else if (produit.getFibre() > 2.8 && produit.getFibre() <= 3.5){
                point_N += 4;
                fibreScore += 4;
            }
            else if (produit.getFibre() > 3.5){
                point_N += 5;
                fibreScore += 5;
            }
            //Protéine
            if(produit.getProteine() > 1.6 && produit.getProteine() <= 3.2) point_N += 1;
            else if (produit.getProteine() > 3.2 && produit.getProteine() <= 4.8) point_N += 2;
            else if (produit.getProteine() > 4.8 && produit.getProteine() <= 6.4) point_N += 3;
            else if (produit.getProteine() > 6.4 && produit.getProteine() <= 8) point_N += 4;
            else if (produit.getProteine() > 8) point_N += 5;
        }
        //****Parraport les matieres grasses ********************
        else if(listeGrassse.contains(produit.getCategorie())){
            //L'énergie
            if (produit.getEnergie() > 335 && produit.getEnergie() <= 670) point_N += 1;
            else if (produit.getEnergie() > 670 && produit.getEnergie() <= 1005) point_N += 2;
            else if (produit.getEnergie() > 1005 && produit.getEnergie() <= 1340) point_N += 3;
            else if (produit.getEnergie() > 1340 && produit.getEnergie() <= 1675) point_N += 4;
            else if (produit.getEnergie() > 1675 && produit.getEnergie() <= 2010) point_N += 5;
            else if (produit.getEnergie() > 2010 && produit.getEnergie() <= 2345) point_N += 6;
            else if (produit.getEnergie() > 2345 && produit.getEnergie() <= 2680) point_N += 7;
            else if (produit.getEnergie() > 2680 && produit.getEnergie() <= 3015) point_N += 8;
            else if (produit.getEnergie() > 3015 && produit.getEnergie() <= 3350) point_N += 9;
            else if (produit.getEnergie() > 3350) point_N += 10;
            // calcul du sucre
            if(produit.getSucre() <= 1.5) point_N += 1;
            else if (produit.getSucre() <= 3 && produit.getSucre() > 1.5) point_N += 2;
            else if (produit.getSucre() <= 4.5 && produit.getSucre() > 3) point_N += 3;
            else if (produit.getSucre() <= 6 && produit.getSucre() > 4.5) point_N += 4;
            else if (produit.getSucre() <= 7.5 && produit.getSucre() > 6) point_N += 5;
            else if (produit.getSucre() <= 9 && produit.getSucre() > 7.5) point_N += 6;
            else if (produit.getSucre() <= 10.5 && produit.getSucre() > 9) point_N += 7;
            else if (produit.getSucre() <= 12 && produit.getSucre() > 10.5) point_N += 8;
            else if (produit.getSucre() <= 13.5 && produit.getSucre() > 12) point_N += 9;
            else if (produit.getSucre() > 13.5) point_N += 10;
            // calcul du gresse saturé
            if(produit.getGraisse() < 16) point_N += 1;
            else if (produit.getGraisse() < 22 && produit.getGraisse() >= 16) point_N += 2;
            else if (produit.getGraisse() < 28 && produit.getGraisse() >= 22) point_N += 3;
            else if (produit.getGraisse() < 34 && produit.getGraisse() >= 28) point_N += 4;
            else if (produit.getGraisse() < 40 && produit.getGraisse() >= 34) point_N += 5;
            else if (produit.getGraisse() < 46 && produit.getGraisse() >= 40) point_N += 6;
            else if (produit.getGraisse() < 52 && produit.getGraisse() >= 46) point_N += 7;
            else if (produit.getGraisse() < 58 && produit.getGraisse() >= 52) point_N += 8;
            else if (produit.getGraisse() < 64 && produit.getGraisse() >= 58) point_N += 9;
            else if (produit.getGraisse() >= 64) point_N += 10;
            // Sodium
            if(produit.getSodium() > 90 && produit.getSodium() <= 180) point_N += 1;
            else if (produit.getSodium()*1000 > 180 && produit.getSodium()*1000 <= 270) point_N += 2;
            else if (produit.getSodium()*1000 > 270 && produit.getSodium()*1000 <= 360) point_N += 3;
            else if (produit.getSodium()*1000 > 360 && produit.getSodium()*1000 <= 450) point_N += 4;
            else if (produit.getSodium()*1000 > 450 && produit.getSodium()*1000 <= 540) point_N += 5;
            else if (produit.getSodium()*1000 > 540 && produit.getSodium()*1000 <= 360) point_N += 6;
            else if (produit.getSodium()*1000 > 630 && produit.getSodium()*1000 <= 720) point_N += 7;
            else if (produit.getSodium()*1000 > 720 && produit.getSodium()*1000 <= 810) point_N += 8;
            else if (produit.getSodium()*1000 > 810 && produit.getSodium()*1000 <= 900) point_N += 9;
            else if (produit.getSodium()*1000 > 900) point_N += 10;
            // calcul du fruits et lesgumes
            if(produit.getFruits_legumes() > 40){
                point_N += 1;
                fruits_legumesScore += 1;
            }
            else if (produit.getFruits_legumes() > 60){
                point_N += 2;
                fruits_legumesScore += 2;
            }
            else if (produit.getFruits_legumes() > 80){
                point_N += 5;
                fruits_legumesScore += 5;
            }
            //Fibres
            if(produit.getFibre() > 0.7) {
                point_N += 1;
                fibreScore += 1;
            }
            else if (produit.getFibre() > 1.4 && produit.getFibre() <= 2.1){
                point_N += 2;
                fibreScore += 2;
            }
            else if (produit.getFibre() > 2.1 && produit.getFibre() <= 2.8){
                point_N += 3;
                fibreScore += 3;
            }
            else if (produit.getFibre() > 2.8 && produit.getFibre() <= 3.5){
                point_N += 4;
                fibreScore += 4;
            }
            else if (produit.getFibre() > 3.5){
                point_N += 5;
                fibreScore += 5;
            }
            //Protéines
            if(produit.getProteine() > 1.6 && produit.getProteine() <= 3.2) point_N += 1;
            else if (produit.getProteine() > 3.2 && produit.getProteine() <= 4.8) point_N += 2;
            else if (produit.getProteine() > 4.8 && produit.getProteine() <= 6.4) point_N += 3;
            else if (produit.getProteine() > 6.4 && produit.getProteine() <= 8) point_N += 4;
            else if (produit.getProteine() > 8) point_N += 5;
        }
        //****Parraport les autres ********************
        else{
            // calcul de l'Energie
            if (produit.getEnergie() > 335 && produit.getEnergie() <= 670) point_N += 1;
            else if (produit.getEnergie() > 670 && produit.getEnergie() <= 1005) point_N += 2;
            else if (produit.getEnergie() > 1005 && produit.getEnergie() <= 1340) point_N += 3;
            else if (produit.getEnergie() > 1340 && produit.getEnergie() <= 1675) point_N += 4;
            else if (produit.getEnergie() > 1675 && produit.getEnergie() <= 2010) point_N += 5;
            else if (produit.getEnergie() > 2010 && produit.getEnergie() <= 2345) point_N += 6;
            else if (produit.getEnergie() > 2345 && produit.getEnergie() <= 2680) point_N += 7;
            else if (produit.getEnergie() > 2680 && produit.getEnergie() <= 3015) point_N += 8;
            else if (produit.getEnergie() > 3015 && produit.getEnergie() <= 3350) point_N += 9;
            else if (produit.getEnergie() > 3350) point_N += 10;
            // calcul du sucre
            if(produit.getSucre() <= 1.5) point_N += 1;
            else if (produit.getSucre() <= 3 && produit.getSucre() > 1.5) point_N += 2;
            else if (produit.getSucre() <= 4.5 && produit.getSucre() > 3) point_N += 3;
            else if (produit.getSucre() <= 6 && produit.getSucre() > 4.5) point_N += 4;
            else if (produit.getSucre() <= 7.5 && produit.getSucre() > 6) point_N += 5;
            else if (produit.getSucre() <= 9 && produit.getSucre() > 7.5) point_N += 6;
            else if (produit.getSucre() <= 10.5 && produit.getSucre() > 9) point_N += 7;
            else if (produit.getSucre() <= 12 && produit.getSucre() > 10.5) point_N += 8;
            else if (produit.getSucre() <= 13.5 && produit.getSucre() > 12) point_N += 9;
            else if (produit.getSucre() > 13.5) point_N += 10;
            // calcul du gresse saturé
            if(produit.getGraisse() > 1 && produit.getGraisse() <=2) point_N += 1;
            else if (produit.getGraisse() > 2 && produit.getGraisse() <=3) point_N += 2;
            else if (produit.getGraisse() > 3 && produit.getGraisse() <=4) point_N += 3;
            else if (produit.getGraisse() > 4 && produit.getGraisse() <=5) point_N += 4;
            else if (produit.getGraisse() > 5 && produit.getGraisse() <=6) point_N += 5;
            else if (produit.getGraisse() > 6 && produit.getGraisse() <=7) point_N += 6;
            else if (produit.getGraisse() > 7 && produit.getGraisse() <=8) point_N += 7;
            else if (produit.getGraisse() > 8 && produit.getGraisse() <=9) point_N += 8;
            else if (produit.getGraisse() > 9 && produit.getGraisse() <=10) point_N += 9;
            else if (produit.getGraisse() > 10) point_N += 10;
            //Sodium
            if(produit.getSodium() > 90 && produit.getSodium() <= 180) point_N += 1;
            else if (produit.getSodium() > 180 && produit.getSodium() <= 270) point_N += 2;
            else if (produit.getSodium() > 270 && produit.getSodium() <= 360) point_N += 3;
            else if (produit.getSodium() > 360 && produit.getSodium() <= 450) point_N += 4;
            else if (produit.getSodium() > 450 && produit.getSodium() <= 540) point_N += 5;
            else if (produit.getSodium() > 540 && produit.getSodium() <= 360) point_N += 6;
            else if (produit.getSodium() > 630 && produit.getSodium() <= 720) point_N += 7;
            else if (produit.getSodium() > 720 && produit.getSodium() <= 810) point_N += 8;
            else if (produit.getSodium() > 810 && produit.getSodium() <= 900) point_N += 9;
            else if (produit.getSodium() > 900) point_N += 10;
            // calcul du fruits et lesgumes
            if(produit.getFruits_legumes() > 40){
                point_N += 1;
                fruits_legumesScore += 1;
            }
            else if (produit.getFruits_legumes() > 60){
                point_N += 2;
                fruits_legumesScore += 2;
            }
            else if (produit.getFruits_legumes() > 80){
                point_N += 5;
                fruits_legumesScore += 5;
            }
            //Fibres
            if(produit.getFibre() > 0.7) {
                point_N += 1;
                fibreScore += 1;
            }
            else if (produit.getFibre() > 1.4 && produit.getFibre() <= 2.1){
                point_N += 2;
                fibreScore += 2;
            }
            else if (produit.getFibre() > 2.1 && produit.getFibre() <= 2.8){
                point_N += 3;
                fibreScore += 3;
            }
            else if (produit.getFibre() > 2.8 && produit.getFibre() <= 3.5){
                point_N += 4;
                fibreScore += 4;
            }
            else if (produit.getFibre() > 3.5){
                point_N += 5;
                fibreScore += 5;
            }
            //Protéines
            if(produit.getProteine() > 1.6 && produit.getProteine() <= 3.2) point_N += 1;
            else if (produit.getProteine() > 3.2 && produit.getProteine() <= 4.8) point_N += 2;
            else if (produit.getProteine() > 4.8 && produit.getProteine() <= 6.4) point_N += 3;
            else if (produit.getProteine() > 6.4 && produit.getProteine() <= 8) point_N += 4;
            else if (produit.getProteine() > 8) point_N += 5;

        }

        ///////// Etape 2 //////////////////////////////////////////////////////////////////////
        if(point_N >= 11){
            if(fruits_legumesScore >= 5){
                point = point_N - point_P;
            }
            else{
                point = point_N - (fibreScore + fruits_legumesScore);
            }
        }
        else{
            point = point_N - point_P;
        }
        ////////// Etape 3 //////////////////////////////////////////////////////////////////////
        if(produit.getCategorie().equals("Boissons")){
            if (point < 1){
                B.setTextColor(getResources().getColor(R.color.white));
                B.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
                B.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
                B.setBackground(getResources().getDrawable(R.drawable.bg_rounded_b));
                B.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
            else if(point >=2 && point <= 5){
                C.setTextColor(getResources().getColor(R.color.white));
                C.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
                C.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
                C.setBackground(getResources().getDrawable(R.drawable.bg_rounded_c));
                C.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
            else if(point >=6 && point <= 9){
                D.setTextColor(getResources().getColor(R.color.white));
                D.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
                D.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
                D.setBackground(getResources().getDrawable(R.drawable.bg_rounded_d));
                D.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
            else if(point >= 10){
                E.setBackground(getResources().getDrawable(R.drawable.bg_rounded_e));
                E.setTextColor(getResources().getColor(R.color.white));
                E.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
                E.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
                E.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
        }
        else if(produit.getCategorie().equals("Eau")){
            A.setTextColor(getResources().getColor(R.color.white));
            A.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
            A.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
            A.setBackground(getResources().getDrawable(R.drawable.bg_rounded_a));
            A.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
        }
        else{
            if(point < -1){
                A.setTextColor(getResources().getColor(R.color.white));
                A.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
                A.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
                A.setBackground(getResources().getDrawable(R.drawable.bg_rounded_a));
                A.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
            else if(point >= 0 && point <= 2){
                B.setTextColor(getResources().getColor(R.color.white));
                B.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
                B.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
                B.setBackground(getResources().getDrawable(R.drawable.bg_rounded_b));
                B.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
            else if(point >= 3 && point <= 10){
                C.setTextColor(getResources().getColor(R.color.white));
                C.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
                C.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
                C.setBackground(getResources().getDrawable(R.drawable.bg_rounded_c));
                C.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
            else if(point >= 11 && point <= 18){
                D.setBackground(getResources().getDrawable(R.drawable.bg_rounded_d));
                D.setTextColor(getResources().getColor(R.color.white));
                D.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
                D.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
                D.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
            else if(point >= 19){
                E.setBackground(getResources().getDrawable(R.drawable.bg_rounded_e));
                E.setTextColor(getResources().getColor(R.color.white));
                E.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
                E.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
                E.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        point = 0;
        point_N = 0;
        point_P = 0;
        fruits_legumesScore = 0;
        fibreScore = 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        point = 0;
        point_N = 0;
        point_P = 0;
        fruits_legumesScore = 0;
        fibreScore = 0;
    }

    public void openDialog(){
        Dialog dialog = new Dialog();
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyNbr(int nbr) {
        SessionManager s = new SessionManager(this);
        String id = s.getID();
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this,queue);
        Bundle b = getIntent().getExtras();
        final String code_Bar = b.getString("code_bar");
        request.ajouterConsommation(id, code_Bar, String.valueOf(nbr), new MyRequest.AddConsCallBack() {
            @Override
            public void onSucces(String message) {
                Toast.makeText(getApplicationContext(), "bravo!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

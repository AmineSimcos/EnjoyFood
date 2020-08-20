package com.exemple.enjoyfood.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.exemple.enjoyfood.Nutriscore;
import com.google.android.material.tabs.TabLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.Locale;

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
    public static Produit produit;
    private ImageView iv;
    private ProgressBar pg;
    private TextView A, B, C, D, E;
    private TextView txtTitre, txtDescription;
    private CircleImageView btn_add;
    private RequestQueue queue;
    private MyRequest request;
    private String id, categorie;
    private int volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);

        Bundle b = getIntent().getExtras();
        final String code_Bar = b.getString("code_bar");
        final String titre = b.getString("titre");
        final String titre_en = b.getString("titre_en");
        final String titre_ar = b.getString("titre_ar");
        final String description = b.getString("description");
        final String desc_en = b.getString("desc_en");
        final String desc_ar = b.getString("desc_ar");
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
        final String ingrediant_en = b.getString("ingrediant_en");
        final String ingrediant_ar = b.getString("ingrediant_ar");
        final int volume = b.getInt("volume");
        this.categorie = categorie;
        this.volume = volume;
        //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
        SessionManager s = new SessionManager(this);
        if(s.isLogged()){
            btn_add = findViewById(R.id._btn_add);
            btn_add.setVisibility(View.VISIBLE);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialog();
                }
            });
            id = s.getID();
            queue = VolleySingleton.getInstance(this).getRequestQueue();
            request = new MyRequest(this,queue);
            request.ajouterHistorique(id, code_Bar, new MyRequest.AddHistoryCallBack() {
                @Override
                public void onSucces(String message) {
                    //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        iv = findViewById(R.id.iv);
        pg = findViewById(R.id.pg_image_produit_resultat);

        TabLayout t = findViewById(R.id.tabLayout);
        t.addTab(t.newTab().setText(getResources().getString(R.string.tableau)));
        t.addTab(t.newTab().setText(getResources().getString(R.string.ingrediants)));
        t.addTab(t.newTab().setText(getResources().getString(R.string.statistique)));
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

        // hadi ta3 in7inae min ykoun en arabe
        if(Locale.getDefault().getLanguage().equals("ar")){
            A.setBackground(getResources().getDrawable(R.drawable.bg_rounded_a_arabe));
            E.setBackground(getResources().getDrawable(R.drawable.bg_rounded_e_arabe));
        }
        ////////////////////////////////////////////////////////////////////////////////////////////


//        Runnable runnable = new Runnable(){
//            @Override
//            public void run(){
//                //démarer une page
//                Intent intent = new Intent(getApplicationContext(),IntroActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        };


        produit = new Produit (code_Bar, titre, titre_en, titre_ar, description, desc_en, desc_ar, image, categorie, energie, matiere_grasse, graisse, glucide, sucre, proteine, fibre, sodium, sel, calicium, fruits_lesgumes, ingrediant, ingrediant_en, ingrediant_ar, volume);
        getSupportActionBar().setTitle(titre);


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
        if(Locale.getDefault().getLanguage().equals("fr")) {
            txtTitre.setText(produit.getTitre());
            txtDescription.setText(produit.getDescription());
        }
        else if(Locale.getDefault().getLanguage().equals("en")){
            txtTitre.setText(produit.getTitre_en());
            txtDescription.setText(produit.getDesc_en());
        }
        else if(Locale.getDefault().getLanguage().equals("ar")){
            txtTitre.setText(produit.getTitre_ar());
            txtDescription.setText(produit.getDesc_ar());
        }

        /////// Vérification Nutri-Score /////////////////////////////////////////////////////////
        String alphabet = Nutriscore.calcul(produit);

        if(alphabet.equals("A")){
            A.setTextColor(getResources().getColor(R.color.white));
            A.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
            A.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
            A.setBackground(getResources().getDrawable(R.drawable.bg_rounded_a));
            A.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
        }
        else if(alphabet.equals("B")){
            B.setTextColor(getResources().getColor(R.color.white));
            B.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
            B.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
            B.setBackground(getResources().getDrawable(R.drawable.bg_rounded_b));
            B.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
        }
        else if(alphabet.equals("C")){
            C.setTextColor(getResources().getColor(R.color.white));
            C.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
            C.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
            C.setBackground(getResources().getDrawable(R.drawable.bg_rounded_c));
            C.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
        }
        else if(alphabet.equals("D")){
            D.setBackground(getResources().getDrawable(R.drawable.bg_rounded_d));
            D.setTextColor(getResources().getColor(R.color.white));
            D.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
            D.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
            D.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
        }
        else if(alphabet.equals("E")){
            E.setBackground(getResources().getDrawable(R.drawable.bg_rounded_e));
            E.setTextColor(getResources().getColor(R.color.white));
            E.getLayoutParams().height = (int) getResources().getDimension(R.dimen.nutri_height_transform_size);
            E.getLayoutParams().width = (int) getResources().getDimension(R.dimen.nutri_width_transform_size);
            E.setTextSize(TypedValue.COMPLEX_UNIT_SP,60);
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
        Bundle args = new Bundle();
        args.putString("categorie", categorie);
        args.putInt("volume", volume);
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void applyNbr(int nbr) {

        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this,queue);
        Bundle b = getIntent().getExtras();
        final String code_Bar = b.getString("code_bar");
        request.ajouterConsommation(id, code_Bar, String.valueOf(nbr), new MyRequest.AddConsCallBack() {
            @Override
            public void onSucces(String message) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.consomme), Toast.LENGTH_SHORT).show();
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

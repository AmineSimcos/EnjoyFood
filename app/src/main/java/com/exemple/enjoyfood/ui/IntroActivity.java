package com.exemple.enjoyfood.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.thekhaeng.pushdownanim.PushDownAnim;

import java.util.ArrayList;
import java.util.List;

import dz.baichoudjedi.lovefood.R;
import dz.baichoudjedi.lovefood.model.ScreenItem;
import dz.baichoudjedi.lovefood.myadapter.IntroViewPagerAdapter;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private IntroViewPagerAdapter introViewPagerAdapter;
    private TabLayout tabIndicator;
    private Button btn_next, btn__getStarted;
    private int position = 0;
    private Animation btn_anim;
    private MediaPlayer mediaplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the activity on full screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        // Hide the action bar
        //getSupportActionBar().hide();

        // when this activity is about to be launch we need to check if its opened before or not

        if(restorePrefData()){

            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        else{
            mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.a);
            mediaplayer.start();
        }




        tabIndicator = findViewById(R.id.tab_indicator);
        btn_next = findViewById(R.id.btn_nxt);
        btn__getStarted = findViewById(R.id.btn_get_started);
        btn_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Bienvenue !", "Bienvenue ! Prêt(e) à changer certaines de vos habitudes et faire plus atention lors de vos achats et consommations ?", R.drawable.bienvenue, R.raw.a));
        mList.add(new ScreenItem("EnjoyFood", "Enjoy Food est une application conçu pour vous aider a mieux choisir les bons produits et a gérer vos consommations", R.drawable.faiza, R.raw.b));
        mList.add(new ScreenItem("Scannez", "Récupérez facilement toutes les informations nutritionnelles d'un produit alimentaire et décider rééllement de ce que vous voulez dans votre assiette", R.drawable.text3, R.raw.c));
        mList.add(new ScreenItem("Consommation", "Notez et calculez vos consommations journalières, hebdomadaires ou mensuelles", R.drawable.e, R.raw.d));
        mList.add(new ScreenItem("", "", R.drawable.d, -1));

        screenPager = findViewById(R.id.vp_screen_pager);
        introViewPagerAdapter = new IntroViewPagerAdapter(this, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);

        PushDownAnim.setPushDownAnimTo(btn_next,btn__getStarted)
                .setScale( MODE_SCALE, 0.89f)
                .setDurationPush( PushDownAnim.DEFAULT_PUSH_DURATION )
                .setDurationRelease( PushDownAnim.DEFAULT_RELEASE_DURATION )
                .setInterpolatorPush( PushDownAnim.DEFAULT_INTERPOLATOR )
                .setInterpolatorRelease( PushDownAnim.DEFAULT_INTERPOLATOR );

        // nnext button click

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if(position < mList.size() - 1){

                    position++;
                    screenPager.setCurrentItem(position);
                }
                else if(position == mList.size() - 1){

                    loadLastScreen();
                }
            }
        });

        tabIndicator.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if(tab.getPosition() == mList.size()-1){

                    mediaplayer.stop();
                    //mediaplayer = MediaPlayer.create(getApplicationContext(), R.raw.d);
                    //mediaplayer.start();

                    loadLastScreen();

                }
                else{
                    mediaplayer.stop();
                    mediaplayer = MediaPlayer.create(getApplicationContext(), mList.get(tab.getPosition()).getSon());
                    mediaplayer.start();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btn__getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaplayer.stop();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                savePrefsData();
                finish();

            }
        });

    }

    private boolean restorePrefData() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = prefs.getBoolean("isIntroOpened", false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefsData() {

        SharedPreferences prefs = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();


    }

    private void loadLastScreen() {

        btn_next.setVisibility(View.INVISIBLE);
        btn__getStarted.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);

        // TODO : add an animation on button get started

        btn__getStarted.setAnimation(btn_anim);
    }
}
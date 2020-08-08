package com.exemple.enjoyfood.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.exemple.enjoyfood.R;
import com.google.android.material.navigation.NavigationView;
import com.shreyaspatil.material.navigationview.MaterialNavigationView;

import com.exemple.enjoyfood.SessionManager;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private AppBarConfiguration mAppBarConfiguration;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final MaterialNavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemStyle(MaterialNavigationView.ITEM_STYLE_ROUND_RIGHT);
        View v = navigationView.getHeaderView(0);

        //tv_name = v.findViewById(R.id.tv_name);
        //tv_logout = v.findViewById(R.id.btn_logout);
        //tv_email = v.findViewById(R.id.tv_email);
        Menu menu = navigationView.getMenu();
        sessionManager = new SessionManager(this);
        if(sessionManager.isLogged()){
            final String pseudo = sessionManager.getPseudo();
            String id = sessionManager.getID();
            String email = sessionManager.getEmail();

            menu.findItem(R.id.nav_login).setVisible(false);
            menu.findItem(R.id.nav_profile).setVisible(true).setTitle(pseudo);
            menu.findItem(R.id.nav_logout).setVisible(true);

        }
        else{
            menu.findItem(R.id.nav_login).setVisible(true);
            menu.findItem(R.id.nav_profile).setVisible(false);
            menu.findItem(R.id.nav_logout).setVisible(false);
        }



        menu.findItem(R.id.nav_logout).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sessionManager.logout();
                finish();
                startActivity(getIntent());

                return true;
            }
        });

        menu.findItem(R.id.nav_login).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle b = new Bundle();
                b.putBoolean("fragment", true);
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtras(b);
                startActivityForResult(i, 1331);
                return true;
            }
        });


        menu.findItem(R.id.nav_login).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Bundle b = new Bundle();
                b.putBoolean("fragment", true);
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtras(b);
                startActivityForResult(i, 1331);
                return true;
            }
        });
//        tv_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                sessionManager.logout();
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_accueil, R.id.nav_produit, R.id.nav_consommation, R.id.nav_historique, R.id.nav_apropos)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bar, menu);
        MenuItem recherche = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(recherche);
        if (searchView != null) {
            searchView.setOnQueryTextListener(this);
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.setContentView(R.layout.activity_main);
        NavigationView navigationView = this.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) MainActivity.this);

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Intent intent = new Intent(this, ListeProduitsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        Bundle b = new Bundle();
        b.putString("query", query);
        b.putString("categorie","");
        intent.putExtras(b);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

}

package com.exemple.enjoyfood.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.Toast;


import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.exemple.enjoyfood.model.Produit;
import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.Config;
import com.exemple.enjoyfood.VolleySingleton;
import com.exemple.enjoyfood.myadapter.MonProduitAdapter;
import com.exemple.enjoyfood.myrequest.MyRequest;

public class ListeProduitsActivity extends AppCompatActivity implements MonProduitAdapter.onItemClickListenr{

    private RecyclerView recycler;
    private MonProduitAdapter monProduitAdapter;
    private ArrayList<Produit> listeProduits;
    private RequestQueue requestQueue, queue;
    private MyRequest request;
    private ProgressBar pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_produits);

        pg  = findViewById(R.id.progress_liste);
        recycler = findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        listeProduits = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        Bundle b = getIntent().getExtras();

        //Toast.makeText(this, query, Toast.LENGTH_SHORT).show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parseJSON();
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this,queue);
    }


    private void parseJSON(){
        String url = Config.URL_ALL_PRODUCT;
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("result");

                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String titre = obj.getString("titre");
                            String description = obj.getString("description");
                            String image = obj.getString("image");
                            String code_bar = obj.getString("code_bar");
                            listeProduits.add(new Produit(image, titre, description, code_bar));
                            monProduitAdapter = new MonProduitAdapter(ListeProduitsActivity.this, listeProduits);
                            recycler.setAdapter(monProduitAdapter);
                            pg.setVisibility(View.GONE);
                            recycler.setVisibility(View.VISIBLE);
                            monProduitAdapter.setOnItemClickListener(ListeProduitsActivity.this);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "JSON object mabghach", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    if(error instanceof NetworkError){
                        Toast.makeText(getApplicationContext(), "Impossible de se connecter", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if(error instanceof VolleyError){
                        Toast.makeText(getApplicationContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
            requestQueue.add(request);
            getSupportActionBar().setTitle("Résultat");


    }

    @Override
    public void onItemClick(int position) {
        final Intent i = new Intent(this, ResultatActivity.class);
        Produit produit = listeProduits.get(position);
        String code = produit.getCode_bar();
        request.informationProduct(code, new MyRequest.InformationCallback() {

            @Override
            public void onSucces(String code_bar, String titre, String description, String image, String categorie, double energie, double matiere_grasse, double graisse, double glucide, double sucre, double proteine,double fibre, double sodium, double sel, double calicium, int fruits_lesgumes, String ingrediant) {
                Bundle b = new Bundle();
                b.putString("code_bar",code_bar);
                b.putString("titre",titre);
                b.putString("description",description);
                b.putString("image",image);
                b.putString("categorie",categorie);
                b.putDouble("energie",energie);
                b.putDouble("matiere_grasse",matiere_grasse);
                b.putDouble("graisse",graisse);
                b.putDouble("glucide",glucide);
                b.putDouble("sucre",sucre);
                b.putDouble("proteine",proteine);
                b.putDouble("fibres",fibre);
                b.putDouble("sodium",sodium);
                b.putDouble("sel",sel);
                b.putDouble("calicium",calicium);
                b.putInt("fruits_lesgumes",fruits_lesgumes);
                b.putString("ingrediant",ingrediant);
                i.putExtras(b);
                startActivity(i);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem recherche = menu.findItem(R.id.action_search);
        SearchView searshView = (SearchView) recherche.getActionView();
        searshView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                monProduitAdapter.getFilter().filter(newText);
                return false;
            }
        });

        ActionBar actionBar = getActionBar();
        //actionBar.setHomeButtonEnabled(true);
        //actionBar.setDisplayShowTitleEnabled(true);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(recherche);
//        if (searchView != null) {
//            searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);
//        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

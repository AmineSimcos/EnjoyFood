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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private String query = "";

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
        query = b.getString("query").trim();
        Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
        //Toast.makeText(this, query, Toast.LENGTH_SHORT).show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        parseJSON();
        queue = VolleySingleton.getInstance(this).getRequestQueue();
        request = new MyRequest(this,queue);
    }


    private void parseJSON(){
        String url = Config.URL_ALL_PRODUCT;
        if(!query.isEmpty()){
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsReponse = new JSONObject(response);
                                JSONArray jsonArray = jsReponse.getJSONArray("result");
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
                            }
                            //Log.d("Response", response);
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            //Log.d("Error.Response", "Erreur");
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("titre", query);
                    return params;
                }
            };
            requestQueue.add(postRequest);
            getSupportActionBar().setTitle(query);
        }
        else {
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);

                            String titre = obj.getString("titre");
                            String description = obj.getString("description");
                            String image = obj.getString("image");
                            String code_bar = obj.getString("code_bar");
                            String categorie = obj.getString("categorie");
                            double energie = obj.getDouble("energie");
                            double matiere_grasse = obj.getDouble("matiere_grasse");
                            double graisse = obj.getDouble("graisse");
                            double glucide = obj.getDouble("glucide");
                            double sucre = obj.getDouble("sucre");
                            double proteine = obj.getDouble("proteine");
                            double fibre = obj.getDouble("fibres");
                            double sodium = obj.getDouble("sodium");
                            double sel = obj.getDouble("sel");
                            double calicium = obj.getDouble("calicium");
                            int fruits_lesgumes = obj.getInt("fruits_lesgumes");
                            String ingrediant = obj.getString("ingrediant");
                            listeProduits.add(new Produit(code_bar, titre, description, image, categorie, energie, matiere_grasse, graisse, glucide, sucre, proteine, fibre, sodium, sel, calicium, fruits_lesgumes, ingrediant));
                            monProduitAdapter = new MonProduitAdapter(ListeProduitsActivity.this, listeProduits);
                            recycler.setAdapter(monProduitAdapter);
                            pg.setVisibility(View.GONE);
                            recycler.setVisibility(View.VISIBLE);
                            monProduitAdapter.setOnItemClickListener(ListeProduitsActivity.this);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        finish();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    if (error instanceof NetworkError) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_cnx), Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (error instanceof VolleyError) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
            requestQueue.add(request);
            getSupportActionBar().setTitle("Résultat");
        }

    }

    @Override
    public void onItemClick(int position) {
        final Intent i = new Intent(this, ResultatActivity.class);
        Produit produit = listeProduits.get(position);
        Bundle b = new Bundle();

        b.putString("code_bar",produit.getCode_bar());
        b.putString("titre",produit.getTitre());
        b.putString("description",produit.getDescription());
        b.putString("image",produit.getImage());
        b.putString("categorie",produit.getCategorie());
        b.putDouble("energie",produit.getEnergie());
        b.putDouble("matiere_grasse",produit.getMatiere_grasse());
        b.putDouble("graisse",produit.getGraisse());
        b.putDouble("glucide",produit.getGlucide());
        b.putDouble("sucre",produit.getSucre());
        b.putDouble("proteine",produit.getProteine());
        b.putDouble("fibres",produit.getFibre());
        b.putDouble("sodium",produit.getSodium());
        b.putDouble("sel",produit.getSel());
        b.putDouble("calicium",produit.getCalicium());
        b.putInt("fruits_lesgumes",produit.getFruits_legumes());
        b.putString("ingrediant",produit.getIngrediant());
        i.putExtras(b);
        startActivity(i);
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
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

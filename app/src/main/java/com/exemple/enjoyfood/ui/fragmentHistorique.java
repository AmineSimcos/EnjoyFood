package com.exemple.enjoyfood.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exemple.enjoyfood.Config;
import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.SessionManager;
import com.exemple.enjoyfood.VolleySingleton;
import com.exemple.enjoyfood.model.Produit;
import com.exemple.enjoyfood.myadapter.HistoriqueAdapter;
import com.exemple.enjoyfood.myadapter.MonProduitAdapter;
import com.exemple.enjoyfood.myrequest.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class fragmentHistorique extends Fragment {

    private RecyclerView recycler;
    private HistoriqueAdapter monProduitAdapter;
    private ArrayList<Produit> listeProduits;
    private RequestQueue requestQueue, queue;
    private MyRequest request;
    private TextView tv_nbr_produit_scannee,tv_vider_historique;
    private String id;
    private SessionManager sessionManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_historique,container,false);

        sessionManager = new SessionManager(getContext());
        id = sessionManager.getID();

        tv_nbr_produit_scannee = v.findViewById(R.id.tv_nbr_produits_scannee);
        tv_vider_historique = v.findViewById(R.id.tv_vider_historique);
        recycler = v.findViewById(R.id.rc_historique);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        listeProduits = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());

        parseJSON();
        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        request = new MyRequest(getContext(),queue);


        tv_vider_historique.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.supprimerHistorique(id, new MyRequest.SuppHistoryCallBack() {
                    @Override
                    public void onSucces(String message) {
                        Toast.makeText(getContext(), "bravo!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
                listeProduits.clear();
                monProduitAdapter = new HistoriqueAdapter(getActivity(), listeProduits);
                recycler.setAdapter(monProduitAdapter);
                tv_nbr_produit_scannee.setText(String.valueOf(listeProduits.size()));
            }
        });

        return v;
    }

    private void parseJSON(){
        listeProduits.clear();
        String url = Config.URL_HISTORY;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
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
                        double energie = obj.getDouble("energie");
                        String categorie = obj.getString("categorie");
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
                        monProduitAdapter = new HistoriqueAdapter(getActivity(), listeProduits);
                        monProduitAdapter.setOnItemClickListener(new HistoriqueAdapter.onItemClickListenr() {
                            @Override
                            public void onItemClick(int position) {
                                final Intent i = new Intent(getContext(), ResultatActivity.class);
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
                        });
                        recycler.setAdapter(monProduitAdapter);
                        //pg.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                        //monProduitAdapter.setOnItemClickListener(ListeProduitsActivity.this);
                        tv_nbr_produit_scannee.setText(String.valueOf(listeProduits.size()));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "JSON object mabghach", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                if(error instanceof NetworkError){
                    Toast.makeText(getContext(), "Impossible de se connecter", Toast.LENGTH_SHORT).show();

                }
                else if(error instanceof VolleyError){
                    Toast.makeText(getContext(), "Une erreur s'est produite", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };
        requestQueue.add(postRequest);
    }

}


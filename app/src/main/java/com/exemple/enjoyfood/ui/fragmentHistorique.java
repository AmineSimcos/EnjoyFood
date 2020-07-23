package com.exemple.enjoyfood.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;
import com.exemple.enjoyfood.Config;
import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.VolleySingleton;
import com.exemple.enjoyfood.model.Produit;
import com.exemple.enjoyfood.myadapter.HistoriqueAdapter;
import com.exemple.enjoyfood.myadapter.MonProduitAdapter;
import com.exemple.enjoyfood.myrequest.MyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class fragmentHistorique extends Fragment {

    private RecyclerView recycler;
    private HistoriqueAdapter monProduitAdapter;
    private ArrayList<Produit> listeProduits;
    private RequestQueue requestQueue, queue;
    private MyRequest request;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_historique,container,false);

        recycler = v.findViewById(R.id.rc_historique);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        listeProduits = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());

        parseJSON();
        queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        request = new MyRequest(getContext(),queue);

//        recycler.OnClickListener(new AdapterView.OnClickListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getContext(), "Ne fonctionne pas!", Toast.LENGTH_SHORT).show();
//                final Intent i = new Intent(getContext(), ResultatActivity.class);
//                Produit produit = listeProduits.get(position);
//                Bundle b = new Bundle();
//                b.putString("code_bar",produit.getCode_bar());
//                b.putString("titre",produit.getTitre());
//                b.putString("description",produit.getDescription());
//                b.putString("image",produit.getImage());
//                b.putString("categorie",produit.getCategorie());
//                b.putDouble("energie",produit.getEnergie());
//                b.putDouble("matiere_grasse",produit.getMatiere_grasse());
//                b.putDouble("graisse",produit.getGraisse());
//                b.putDouble("glucide",produit.getGlucide());
//                b.putDouble("sucre",produit.getSucre());
//                b.putDouble("proteine",produit.getProteine());
//                b.putDouble("fibres",produit.getFibre());
//                b.putDouble("sodium",produit.getSodium());
//                b.putDouble("sel",produit.getSel());
//                b.putDouble("calicium",produit.getCalicium());
//                b.putInt("fruits_lesgumes",produit.getFruits_legumes());
//                b.putString("ingrediant",produit.getIngrediant());
//                i.putExtras(b);
//                startActivity(i);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        return v;
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
                        monProduitAdapter = new HistoriqueAdapter(getActivity(), listeProduits);
                        recycler.setAdapter(monProduitAdapter);
                        //pg.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                        //monProduitAdapter.setOnItemClickListener(ListeProduitsActivity.this);

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
        });
        requestQueue.add(request);
    }

}


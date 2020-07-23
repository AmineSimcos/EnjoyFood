package com.exemple.enjoyfood.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


    public void onItemClick(int position) {
        final Intent i = new Intent(getContext(), ResultatActivity.class);
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
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

}


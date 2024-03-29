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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class fragmentHistorique extends Fragment {

    private RecyclerView recycler;
    private HistoriqueAdapter monProduitAdapter;
    private ArrayList<Produit> listeProduits;
    private RequestQueue requestQueue, queue;
    private MyRequest request;
    private TextView tv_nbr_produit_scannee,tv_vider_historique;
    private Button btn_connexion, btn_inscription;
    private LinearLayout page_cnx;
    private RelativeLayout page_historique;
    private String id;
    private SessionManager sessionManager;
    private final int CODE = 1331;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_historique,container,false);

        page_cnx = v.findViewById(R.id.page_cnx);
        page_historique = v.findViewById(R.id.page_historique);

        sessionManager = new SessionManager(getContext());

        if(sessionManager.isLogged()){
            page_cnx.setVisibility(View.GONE);
            page_historique.setVisibility(View.VISIBLE);

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

            if(Config.ANIMATION_BUTTON_ACTIVE) {
                PushDownAnim.setPushDownAnimTo(tv_vider_historique)
                        .setScale(MODE_SCALE, 0.89f)
                        .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                        .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                        .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                        .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
            };


            // Vérifier si l'historique est vide, la touche ne fonctionne pas

                tv_vider_historique.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        request.supprimerHistorique(id, new MyRequest.SuppHistoryCallBack() {
                            @Override
                            public void onSucces(String message) {
                                Toast.makeText(getContext(), getResources().getString(R.string.historique_empty), Toast.LENGTH_SHORT).show();
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


        }
        else{
            page_cnx.setVisibility(View.VISIBLE);
            page_historique.setVisibility(View.GONE);
            btn_connexion = v.findViewById(R.id.btn_page_cnx);
            btn_inscription = v.findViewById(R.id.btn_page_incription);
            btn_connexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getContext(), LoginActivity.class), CODE);
                }
            });

            btn_inscription.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(new Intent(getContext(), RegisterActivity.class), CODE);
                }
            });
        }


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
                        String titre_en = obj.getString("titre_en");
                        String titre_ar = obj.getString("titre_ar");
                        String description = obj.getString("description");
                        String desc_en = obj.getString("desc_en");
                        String desc_ar = obj.getString("desc_ar");
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
                        String ingrediant_en = obj.getString("ingrediant_en");
                        String ingrediant_ar = obj.getString("ingrediant_ar");
                        int volume = obj.getInt("volume");
                        listeProduits.add(new Produit(code_bar, titre, titre_en, titre_ar, description, desc_en, desc_ar, image, categorie, energie, matiere_grasse, graisse, glucide, sucre, proteine, fibre, sodium, sel, calicium, fruits_lesgumes, ingrediant, ingrediant_en, ingrediant_ar, volume));
                        monProduitAdapter = new HistoriqueAdapter(getActivity(), listeProduits);
                        monProduitAdapter.setOnItemClickListener(new HistoriqueAdapter.onItemClickListenr() {
                            @Override
                            public void onItemClick(int position) {
                                final Intent i = new Intent(getContext(), ResultatActivity.class);
                                Produit produit = listeProduits.get(position);
                                Bundle b = new Bundle();
                                b.putString("code_bar",produit.getCode_bar());
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
                    //Toast.makeText(getContext(), "Problème de JSON", Toast.LENGTH_SHORT).show();
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


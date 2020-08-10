package com.exemple.enjoyfood.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.exemple.enjoyfood.Config;
import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.VolleySingleton;
import com.exemple.enjoyfood.model.Produit;
import com.exemple.enjoyfood.myadapter.GridViewAdapter;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class fragmentProduit extends Fragment {

    private String categorie;
    private GridView gv;
    private CircleImageView iv_Boissons, iv_sucre, iv_fruits_legumes, iv_feculents, iv_lait, iv_gras, iv_eau;
    private Button allProducts;
    private TextView tv_subTitle, tv_msg;
    private GridViewAdapter monProduitAdapter;
    private ArrayList<Produit> listeProduits;
    private RequestQueue requestQueue;
    private ProgressBar pg;
    private HashMap<String, String> cle_categorie = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_produit,container,false);
        requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        allProducts = v.findViewById(R.id.iv_all_product);
        listeProduits = new ArrayList<>();

        for (int i = 0; i < Config.CATEGORIES.length - 1; i++){
            cle_categorie.put(Config.CATEGORIES[i], getResources().getStringArray(R.array.categories)[i]);
        }

        iv_Boissons = v.findViewById(R.id.iv_boisssons);
        iv_fruits_legumes = v.findViewById(R.id.iv_fruit_legumes);
        iv_feculents = v.findViewById(R.id.iv_feculents);
        iv_lait = v.findViewById(R.id.iv_laitieres);
        iv_sucre = v.findViewById(R.id.iv_sucre);
        iv_gras = v.findViewById(R.id.iv_gras);
        iv_eau = v.findViewById(R.id.iv_eau);

        gv = v.findViewById(R.id.gridListView);
        pg = v.findViewById(R.id.progress_for_grid);
        tv_subTitle = v.findViewById(R.id.tv_subTitle);
        tv_msg = v.findViewById(R.id.tv_msg);
        tv_msg.setVisibility(View.INVISIBLE);
        categorie = "Boissons";
        tv_subTitle.setText(cle_categorie.get(categorie));
        parseJSON(categorie);

        // Activer l'animation du boutton
        if(Config.ANIMATION_BUTTON_ACTIVE) {
            PushDownAnim.setPushDownAnimTo(allProducts, iv_Boissons, iv_fruits_legumes, iv_feculents, iv_lait, iv_sucre, iv_gras, iv_eau)
                    .setScale(MODE_SCALE, 0.89f)
                    .setDurationPush(PushDownAnim.DEFAULT_PUSH_DURATION)
                    .setDurationRelease(PushDownAnim.DEFAULT_RELEASE_DURATION)
                    .setInterpolatorPush(PushDownAnim.DEFAULT_INTERPOLATOR)
                    .setInterpolatorRelease(PushDownAnim.DEFAULT_INTERPOLATOR);
        }

        allProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listeProduits.clear();
                startActivity(new Intent(getContext(),ListeProduitsActivity.class));
            }
        });

        iv_Boissons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton(Config.CATEGORIES[0]);
            }
        });

        iv_lait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton(Config.CATEGORIES[1]);
            }
        });

        iv_gras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton(Config.CATEGORIES[2]);
            }
        });

        iv_sucre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton(Config.CATEGORIES[3]);
            }
        });

        iv_feculents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton(Config.CATEGORIES[4]);
            }
        });

        iv_fruits_legumes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton(Config.CATEGORIES[5]);
            }
        });

        iv_eau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton(Config.CATEGORIES[6]);
            }
        });

        monProduitAdapter = new GridViewAdapter(getActivity(), listeProduits);
        gv.setAdapter(monProduitAdapter);
//        gv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
//                Toast.makeText(getContext(), "Remplir bundle", Toast.LENGTH_SHORT).show();
//                startActivity(i);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
//                Toast.makeText(getContext(), "Remplir bundle", Toast.LENGTH_SHORT).show();
//                startActivity(i);
//            }
//        });
        return v;
    }

    private void parseJSON(final String categorie){
        String url = Config.URL_ALL_PRODUCT;
        tv_msg.setVisibility(View.GONE);
        gv.setVisibility(View.GONE);
        pg.setVisibility(View.VISIBLE);
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
                                    String cat = obj.getString("categorie");
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
                                    listeProduits.add(new Produit(code_bar, titre, description, image, cat, energie, matiere_grasse, graisse, glucide, sucre, proteine, fibre, sodium, sel, calicium, fruits_lesgumes, ingrediant));
                                    pg.setVisibility(View.GONE);
                                    //Log.e("Fragment", listeProduits.get(0).getImage());
//                                    monProduitAdapter = new GridViewAdapter(getActivity(), listeProduits);
//                                    gv.setAdapter(monProduitAdapter);
                                    pg.setVisibility(View.GONE);
                                    gv.setVisibility(View.VISIBLE);

//                                    monProduitAdapter.setOnItemClickListener(ResultatActivity.this);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                pg.setVisibility(View.GONE);
                                tv_msg.setVisibility(View.VISIBLE);
                                tv_msg.setText(getResources().getString(R.string.verification));
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", "Erreur");
                            pg.setVisibility(View.GONE);
                            tv_msg.setVisibility(View.VISIBLE);
                            tv_msg.setText(getResources().getString(R.string.error_cnx));
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<>();
                    params.put("categorie", categorie);
                    return params;
                }
            };
            requestQueue.add(postRequest);

        }
        private void boutton(String categorie){
            listeProduits.clear();
            //this.categorie = categorie;
            tv_subTitle.setText(cle_categorie.get(categorie));
            parseJSON(categorie);
        }


}

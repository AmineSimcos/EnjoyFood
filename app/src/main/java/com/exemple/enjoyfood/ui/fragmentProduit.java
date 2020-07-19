package com.exemple.enjoyfood.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.thekhaeng.pushdownanim.PushDownAnim;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import dz.baichoudjedi.lovefood.URLs;
import dz.baichoudjedi.lovefood.VolleySingleton;
import dz.baichoudjedi.lovefood.model.Categorie;
import dz.baichoudjedi.lovefood.ExpandableListDataPump;
import dz.baichoudjedi.lovefood.R;
import dz.baichoudjedi.lovefood.model.Produit;
import dz.baichoudjedi.lovefood.myadapter.CustomExpandableListAdapter;
import dz.baichoudjedi.lovefood.myadapter.GridViewAdapter;
import dz.baichoudjedi.lovefood.myadapter.MonProduitAdapter;
import dz.baichoudjedi.lovefood.myrequest.MyRequest;

import static com.thekhaeng.pushdownanim.PushDownAnim.MODE_SCALE;

public class fragmentProduit extends Fragment {

    private String categorie;
    private RequestQueue queue;
    private MyRequest request;
    private ListView listView;
    private MonProduitAdapter adapter;
    private LinearLayout exp;
    private GridView gv;
    private CircleImageView allProducts, iv_Boissons, iv_fruitsLegumes, iv_feculents, iv_lait, iv_viandes, iv_gras, iv_eau;
    TextView tv_subTitle, tv_msg;
    private GridViewAdapter monProduitAdapter;
    private ArrayList<Produit> listeProduits;
    private RequestQueue requestQueue;
    ProgressBar pg;
    Context context;
    private int layoutResourceId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_produit,container,false);
        requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        allProducts = v.findViewById(R.id.iv_all_product);
        listeProduits = new ArrayList<>();


        iv_Boissons = v.findViewById(R.id.iv_boisssons);
        iv_fruitsLegumes = v.findViewById(R.id.iv_fruitlegumes);
        iv_feculents = v.findViewById(R.id.iv_feculents);
        iv_lait = v.findViewById(R.id.iv_laitieres);
        iv_viandes = v.findViewById(R.id.iv_viandes);
        iv_gras = v.findViewById(R.id.iv_gras);
        iv_eau = v.findViewById(R.id.iv_eau);
        gv = v.findViewById(R.id.gridListView);
        pg = v.findViewById(R.id.progress_for_grid);
        tv_subTitle = v.findViewById(R.id.tv_subTitle);
        tv_msg = v.findViewById(R.id.tv_msg);
        tv_msg.setVisibility(View.INVISIBLE);
        categorie = "Boissons";
        tv_subTitle.setText(categorie);
        parseJSON();

        PushDownAnim.setPushDownAnimTo(allProducts,iv_Boissons,iv_fruitsLegumes,iv_feculents,iv_lait,iv_viandes,iv_gras,iv_eau)
                .setScale( MODE_SCALE, 0.89f)
                .setDurationPush( PushDownAnim.DEFAULT_PUSH_DURATION )
                .setDurationRelease( PushDownAnim.DEFAULT_RELEASE_DURATION )
                .setInterpolatorPush( PushDownAnim.DEFAULT_INTERPOLATOR )
                .setInterpolatorRelease( PushDownAnim.DEFAULT_INTERPOLATOR );

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
                boutton("Boissons");
            }
        });
        iv_fruitsLegumes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton("Fruits et légumes");
            }
        });
        iv_feculents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton("Céréales et féculents");
            }
        });
        iv_lait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton("Produits laitieres");
            }
        });
        iv_viandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton("Viandes, poissons, oeufs");
            }
        });
        iv_gras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton("Corps gras");
            }
        });
        iv_eau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boutton("Eau");
            }
        });

        gv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Ne fonctionne pas!", Toast.LENGTH_SHORT).show();
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

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return v;
    }

    private void parseJSON(){
        String url = URLs.URL_ALL_PRODUCT;
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
                                    Log.e("Fragment", titre);
                                    listeProduits.add(new Produit(image, titre, description, code_bar));
                                    pg.setVisibility(View.GONE);
                                    //Log.e("Fragment", listeProduits.get(0).getImage());
                                    monProduitAdapter = new GridViewAdapter(getActivity(), listeProduits);
                                    gv.setAdapter(monProduitAdapter);
                                    pg.setVisibility(View.GONE);
                                    gv.setVisibility(View.VISIBLE);

//                                    monProduitAdapter.setOnItemClickListener(ResultatActivity.this);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                pg.setVisibility(View.GONE);
                                tv_msg.setVisibility(View.VISIBLE);
                                tv_msg.setText("Aucun produit se trouvé");
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
                            tv_msg.setText("Problem de connexion");
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("categorie", categorie);
                    return params;
                }
            };
            requestQueue.add(postRequest);

        }
        private void boutton(String categorie){
            listeProduits.clear();
            this.categorie = categorie;
            tv_subTitle.setText(categorie);
            parseJSON();
        }


}

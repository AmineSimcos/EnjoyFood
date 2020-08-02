package com.exemple.enjoyfood.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exemple.enjoyfood.R;
import com.exemple.enjoyfood.SessionManager;
import com.exemple.enjoyfood.Config;
import com.exemple.enjoyfood.VolleySingleton;

import static android.app.Activity.RESULT_OK;

public class fragmentConsommation extends Fragment {

    private double Nenergie = 0, Nmatiere_grasse = 0, Ngraisee = 0, Nglucide = 0, Nsucre = 0, Nproteine = 0, Nfibre = 0, Nsodium = 0, Nsel = 0, Ncalcium = 0;
    private int time = 1;
    private TextView tv_cal, tv_glucide, tv_sucre, tv_matiere_grasse, tv_graisse, tv_proteine, tv_fibre, tv_sodium, tv_sel, tv_calcium;
    private Button btn_connexion, btn_inscription;
    private MultiStateToggleButton toogle;
    private LinearLayout page_cnx, page_consommation;
    private SessionManager sessionManager;
    private String id;
    private final int CODE = 1331;

    private RequestQueue requestQueue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_consommation, container, false);

        page_cnx = v.findViewById(R.id.page_cnx);
        page_consommation = v.findViewById(R.id.page_consommation);

        requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();

        sessionManager = new SessionManager(getContext());

        if(sessionManager.isLogged()){
            page_cnx.setVisibility(View.GONE);
            page_consommation.setVisibility(View.VISIBLE);

            id = sessionManager.getID();

            tv_cal = v.findViewById(R.id.tv_calori_total);
            tv_glucide = v.findViewById(R.id.tv_glucide_total);
            tv_sucre = v.findViewById(R.id.tv_sucre_total);
            tv_matiere_grasse  = v.findViewById(R.id.tv_matiere_grasse_total);
            tv_graisse = v.findViewById(R.id.tv_graisse_total);
            tv_proteine = v.findViewById(R.id.tv_proteine_total);
            tv_fibre = v.findViewById(R.id.tv_fibre_total);
            tv_sodium = v.findViewById(R.id.tv_sodium_total);
            tv_sel = v.findViewById(R.id.tv_sel_total);
            tv_calcium = v.findViewById(R.id.tv_calcium_total);

            toogle = v.findViewById(R.id.mstb_multi_id);
            toogle.setElements(R.array.bilan, 0);
            toogle.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
                @Override
                public void onValueChanged(int position) {
                    switch (position){
                        case 0:
                            time = 1;
                            break;
                        case 1:
                            time = 7;
                            break;
                        case 2:
                            time = 30;
                            break;
                        default:
                            break;
                    }

                    Nenergie = 0;
                    Nmatiere_grasse = 0;
                    Ngraisee = 0;
                    Nglucide = 0;
                    Nsucre = 0;
                    Nproteine = 0;
                    Nfibre = 0;
                    Nsodium = 0;
                    Nsel = 0;
                    Ncalcium = 0;

                    parseJSON();
                }
            });

            parseJSON();
        }
        else{
            page_cnx.setVisibility(View.VISIBLE);
            page_consommation.setVisibility(View.GONE);
            btn_connexion = v.findViewById(R.id.btn_page_cnx);
            btn_inscription = v.findViewById(R.id.btn_page_incription);

            btn_connexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putBoolean("fragment", true);
                    Intent i = new Intent(getContext(), LoginActivity.class);
                    i.putExtras(b);
                    startActivityForResult(i, CODE);
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
        String url = Config.URL_CONSOMMATION;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsReponse = new JSONObject(response);
                            JSONArray jsonArray = jsReponse.getJSONArray("result");
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject obj = jsonArray.getJSONObject(i);
                                LocalDateTime myDateObj = LocalDateTime.now();
                                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String formattedDate = myDateObj.format(myFormatObj);
                                LocalDateTime d2 = LocalDateTime.parse(formattedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                                LocalDateTime date = LocalDateTime.parse(obj.getString("date_cons"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                                Duration diff = Duration.between(date, d2);
                                long diffDays = diff.toDays();
                                Log.e("diff", String.valueOf(diffDays));
                                if(diffDays <= time){
                                    double energie = Double.parseDouble(obj.getString("energie"));
                                    double matiere_grasse = Double.parseDouble(obj.getString("matiere_grasse"));
                                    double graisse = Double.parseDouble(obj.getString("graisse"));
                                    double glucide = Double.parseDouble(obj.getString("glucide"));
                                    double sucre = Double.parseDouble(obj.getString("sucre"));
                                    double proteine = Double.parseDouble(obj.getString("proteine"));
                                    double fibres = Double.parseDouble(obj.getString("fibres"));
                                    double sodium = Double.parseDouble(obj.getString("sodium"));
                                    double sel = Double.parseDouble(obj.getString("sel"));
                                    double calicium = Double.parseDouble(obj.getString("calicium"));
                                    double nombre = Double.parseDouble(obj.getString("nombre"));
                                    //Log.e("glucide", String.valueOf(glucide));

                                    Nenergie += energie * nombre;
                                    Nmatiere_grasse += matiere_grasse * nombre;
                                    Ngraisee += graisse * nombre;
                                    Nglucide += glucide * nombre;
                                    Nsucre += sucre * nombre;
                                    Nproteine += proteine * nombre;
                                    Nfibre += fibres * nombre;
                                    Nsodium += sodium * nombre;
                                    Nsel += sel * nombre;
                                    Ncalcium += calicium * nombre;
                                    //Log.e("Nglucide", String.valueOf(Nglucide));
//                                Log.e("Fragment", calorie);
                                }

                            }

                            tv_cal.setText(String.format("%.2f", Nenergie) + " KJ");
                            tv_glucide.setText(String.format("%.2f", Nglucide) + " g");
                            tv_sucre.setText(String.format("%.2f", Nsucre) + " g");
                            tv_matiere_grasse.setText(String.format("%.2f", Nmatiere_grasse) + " g");
                            tv_graisse.setText(String.format("%.2f", Ngraisee) + " g");
                            tv_proteine.setText(String.format("%.2f", Nproteine) + " g");
                            tv_fibre.setText(String.format("%.2f", Nfibre) + " g");
                            tv_sodium.setText(String.format("%.2f", Nsodium) + " g");
                            tv_sel.setText(String.format("%.2f", Nsel) + " g");
                            tv_calcium.setText(String.format("%.2f", Ncalcium) + " g");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", "Erreur");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };
        requestQueue.add(postRequest);

    }

    @Override
    public void onActivityResult(int requestCode, int
            resultCode, Intent data) {
        if (requestCode == CODE && resultCode == RESULT_OK) {

        }
    }
}


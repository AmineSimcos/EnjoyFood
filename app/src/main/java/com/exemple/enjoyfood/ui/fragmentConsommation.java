package com.exemple.enjoyfood.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.exemple.enjoyfood.URLs;
import com.exemple.enjoyfood.VolleySingleton;

public class fragmentConsommation extends Fragment {
    double Ncalorie = 0;
    double Nglucide = 0;
    double Nlipide = 0;
    double Nproteine = 0;
    int time = 1;
    TextView tv_cal_total, tv_glucide_total, tv_lipide_total, tv_proteine_total;
    SessionManager sessionManager;
    String id;

    private RequestQueue requestQueue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_consommation, container, false);
        requestQueue = VolleySingleton.getInstance(getContext()).getRequestQueue();
        sessionManager = new SessionManager(getContext());
        id = sessionManager.getID();

        if(sessionManager.isLogged()){
            final String pseudo = sessionManager.getPseudo();
            String id = sessionManager.getID();
            String email = sessionManager.getEmail();
        }

        Spinner sp = v.findViewById(R.id.spinner);
        List<String> list = new ArrayList<>();
        list.add("Jour");
        list.add("Semaine");
        list.add("Moi");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item, list);
        sp.setAdapter(dataAdapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String response = parent.getItemAtPosition(position).toString();
                if(response.equals("Jour")) time = 1;
                else if(response.equals("Semaine")) time = 7;
                else if(response.equals("Moi")) time = 30;
                Ncalorie = 0;
                Nglucide = 0;
                Nlipide = 0;
                Nproteine = 0;
                parseJSON();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        parseJSON();
        tv_cal_total = v.findViewById(R.id.tv_calori_total);
        tv_glucide_total = v.findViewById(R.id.tv_glucide_total);
        tv_lipide_total = v.findViewById(R.id.tv_lipide_total);
        tv_proteine_total = v.findViewById(R.id.tv_proteine_total);


        Log.e("glucide la fin", String.valueOf(Nglucide));
        return v;
    }

    private void parseJSON(){
        String url = URLs.URL_CONSOMMATION;
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
                                    double calorie = Double.parseDouble(obj.getString("energie"));
                                    double glucide = Double.parseDouble(obj.getString("glucide"));
                                    double lipide = Double.parseDouble(obj.getString("matiere_grasse"));
                                    double proteine = Double.parseDouble(obj.getString("proteine"));
                                    double nombre = Double.parseDouble(obj.getString("nombre"));
                                    //Log.e("glucide", String.valueOf(glucide));
                                    Ncalorie += calorie * nombre;
                                    Nglucide += glucide * nombre;
                                    Nlipide += lipide * nombre;
                                    Nproteine += proteine * nombre;
                                    Log.e("Nglucide", String.valueOf(Nglucide));
//                                Log.e("Fragment", calorie);
                                }

                            }
                            tv_cal_total.setText(String.format("%.2f", Ncalorie) + " KJ");
                            tv_glucide_total.setText(String.format("%.2f", Nglucide) + " g");
                            tv_lipide_total.setText(String.format("%.2f", Nlipide) + " g");
                            tv_proteine_total.setText(String.format("%.2f", Nproteine) + " g");
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
}


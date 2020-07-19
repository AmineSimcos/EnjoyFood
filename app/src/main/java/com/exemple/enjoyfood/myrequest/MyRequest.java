package com.exemple.enjoyfood.myrequest;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.exemple.enjoyfood.URLs;

public class MyRequest {

    private RequestQueue queue;


    public MyRequest(Context context, RequestQueue queue){
        this.queue = queue;
    }

    public void register(final String pseudo, final String email, final String password, final String password2, final RegisterCallBack callback){

        StringRequest request = new StringRequest(Request.Method.POST, URLs.URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, String> errors = new HashMap<>();
                try {
                    JSONObject json = new JSONObject(response);
                    boolean error = json.getBoolean("error");

                    if(!error){
                        // l'inscription s'est bien déroulée
                        callback.onSucces("Vous êtes bien inscrit");
                    }
                    else{
                        JSONObject messages = json.getJSONObject("message");
                        if(messages.has("pseudo")){
                            errors.put("pseudo",messages.getString("pseudo"));
                        }
                        if(messages.has("email")){
                            errors.put("email",messages.getString("email"));
                        }
                        if(messages.has("pseudo")){
                            errors.put("password",messages.getString("password"));
                        }
                        callback.inputErrors(errors);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Log.d("APP",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }
                else if(error != null){
                    callback.onError("Une erreur s'est produite");
                }
                //Log.d("APP","ERROR " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("pseudo", pseudo);
                map.put("email", email);
                map.put("password", password);
                map.put("password2", password2);
                return map;
            }
        };

        queue.add(request);
    }

    public interface RegisterCallBack{
        void onSucces(String message);
        void inputErrors(Map<String, String> errors);
        void onError(String message);
    }

    public void connection(final String pseudo, final String password, final LoginCallBack callback){

        StringRequest request = new StringRequest(Request.Method.POST, URLs.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("APP",response);  pour le test
                JSONObject json;
                try {
                    json = new JSONObject(response);
                    boolean error = json.getBoolean("error");

                    if(!error){
                        String id = json.getString("id");
                        String pseudo = json.getString("pseudo");
                        String email = json.getString("email");
                        callback.onSucces(id, pseudo, email);
                    }
                    else{
                        callback.onError(json.getString("message"));
                    }

                } catch (JSONException e) {
                    callback.onError("Une erreur s'est produite");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }
                else if(error != null){
                    callback.onError("Une erreur s'est produite");
                }
                //Log.d("APP","ERROR " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("pseudo", pseudo);
                map.put("password", password);
                return map;
            }
        };

        queue.add(request);
    }

    public interface LoginCallBack{
        void onSucces(String id, String pseudo, String email);
        void onError(String message);
    }

    public void informationProduct(final String code_bar, final InformationCallback callback){

        StringRequest request = new StringRequest(Request.Method.POST, URLs.URL_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject json;
                    json = new JSONObject(response);

                        boolean error = json.getBoolean("error");
                        if (!error) {
                            String code_bar = json.getString("code_bar");
                            String titre = json.getString("titre");
                            String description = json.getString("description");
                            String image = json.getString("image");
                            String categorie = json.getString("categorie");
                            double energie = json.getDouble("energie");
                            double matiere_grasse = json.getDouble("matiere_grasse");
                            double graisse = json.getDouble("graisse");
                            double glucide = json.getDouble("glucide");
                            double sucre = json.getDouble("sucre");
                            double proteine = json.getDouble("proteine");
                            double fibre = json.getDouble("fibres");
                            double sodium = json.getDouble("sodium");
                            double sel = json.getDouble("sel");
                            double calicium = json.getDouble("calicium");
                            int fruits_lesgumes = json.getInt("fruits_lesgumes");
                            String ingrediant = json.getString("ingrediant");
                            callback.onSucces(code_bar, titre, description, image, categorie, energie, matiere_grasse, graisse, glucide, sucre, proteine, fibre, sodium, sel, calicium, fruits_lesgumes, ingrediant);
                        } else {
                            callback.onError(json.getString("message"));
                        }
                    Log.e("Reponse ::", response);
                } catch (JSONException e) {
                    callback.onError("Une erreur s'est produite: " + e);

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error instanceof NetworkError){
                        callback.onError("Impossible de se connecter");
                    }
                    else if(error != null){
                        callback.onError("Une erreur s'est produite");
                    }
                }
            }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<>();
                    map.put("code_bar", code_bar);
                    return map;
                }
            };

        queue.add(request);
    }

    public interface InformationCallback{
        void onSucces(String code_bar, String titre, String description, String image, String categorie, double energie, double matiere_grasse, double graisse, double glucide, double sucre, double proteine, double fibre, double sodium, double sel, double calicium, int fruits_lesgumes, String ingrediant);
        void onError(String message);
    }

    public void ajouterConsommation(final String id, final String code_bar, final String nombre, final AddConsCallBack callback){

        StringRequest request = new StringRequest(Request.Method.POST, URLs.URL_ADD_CONSOMMATION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d("APP",response);  pour le test
                JSONObject json;
                try {
                    json = new JSONObject(response);
                    boolean error = json.getBoolean("error");

                    if(!error){
                        callback.onSucces("ajouter a la liste de la consommation");
                    }
                    else{
                        callback.onError(json.getString("message"));
                    }

                } catch (JSONException e) {
                    callback.onError("Une erreur s'est produite");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error instanceof NetworkError){
                    callback.onError("Impossible de se connecter");
                }
                else if(error != null){
                    callback.onError("Une erreur s'est produite");
                }
                //Log.d("APP","ERROR " + error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                map.put("code_bar", code_bar);
                map.put("nombre", nombre);
                return map;
            }
        };

        queue.add(request);
    }

    public interface AddConsCallBack{
        void onSucces(String message);
        void onError(String message);
    }

}
